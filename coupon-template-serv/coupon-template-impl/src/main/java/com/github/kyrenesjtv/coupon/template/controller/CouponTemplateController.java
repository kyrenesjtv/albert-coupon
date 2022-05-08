package com.github.kyrenesjtv.coupon.template.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.github.kyrenesjtv.coupon.template.api.beans.CouponTemplateInfo;
import com.github.kyrenesjtv.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.github.kyrenesjtv.coupon.template.api.beans.TemplateSearchParams;
import com.github.kyrenesjtv.coupon.template.service.CouponTemplateService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/template")
public class CouponTemplateController {

    @Autowired
    private CouponTemplateService couponTemplateService;

    // 创建优惠券
    @PostMapping("/addTemplate")
    public CouponTemplateInfo addTemplate(@Valid @RequestBody CouponTemplateInfo request) {
        log.info("add coupon template: data={}", request);
        return couponTemplateService.createTemplate(request);
    }

    @PostMapping("/cloneTemplate")
    public CouponTemplateInfo cloneTemplate(@RequestParam("id") Long templateId) {
        log.info("Clone coupon template: data={}", templateId);
        return couponTemplateService.cloneTemplate(templateId);
    }

    // 读取优惠券
    @GetMapping("/getTemplate")
    @SentinelResource(value = "getTemplate",blockHandler = "getTemplate_block")
    public CouponTemplateInfo getTemplate(@RequestParam("id") Long id){
        log.info("get template, id={}", id);
        return couponTemplateService.loadTemplateInfo(id);
    }

    // 批量获取
    @GetMapping("/getBatch")
    @SentinelResource(value = "getTemplateInBatch",blockHandler = "getTemplateInBatch_block",fallback = "getTemplateInBatch_back")//blockHandler只针对BlockException，fallback针对RuntiomException
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids) {
        log.info("get TemplateInBatch: {}", JSON.toJSONString(ids));
        //抛出runtionException
        if(ids.size() > 2){
            List<Integer> integers = new ArrayList<>();
            Integer integer = integers.get(10);
        }
        return couponTemplateService.getTemplateInfoMap(ids);
    }

    // 搜索模板
    @PostMapping("/search")
    public PagedCouponTemplateInfo search(@Valid @RequestBody TemplateSearchParams request) {
        log.info("search templates, payload={}", request);
        return couponTemplateService.search(request);
    }

    // 优惠券无效化
    @DeleteMapping("/deleteTemplate")
    public void deleteTemplate(@RequestParam("id") Long id){
        log.info("deleye template, id={}", id);
        couponTemplateService.deleteTemplate(id);
    }

    public Map<Long, CouponTemplateInfo> getTemplateInBatch_block( Collection<Long> ids, BlockException exception) {
        log.info("getTemplateInBatch接口被限流"); return Maps.newHashMap();
    }
    public Map<Long, CouponTemplateInfo> getTemplateInBatch_back( Collection<Long> ids) {
        log.info("getTemplateInBatch接口被降级"); return Maps.newHashMap();
    }

    public CouponTemplateInfo getTemplate_block( Long id, BlockException exception) {
        log.info("getTemplate接口被限流"); return null;
    }
}
