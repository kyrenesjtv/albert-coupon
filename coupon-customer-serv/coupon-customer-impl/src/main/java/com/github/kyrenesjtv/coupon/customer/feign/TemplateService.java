package com.github.kyrenesjtv.coupon.customer.feign;

import com.github.kyrenesjtv.coupon.customer.feign.fallback.TemplateServiceFallbackFactory;
import com.github.kyrenesjtv.coupon.template.api.beans.CouponTemplateInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Map;

@FeignClient(value = "coupon-template-serv", path = "/template"
//        ,fallback = TemplateServiceFallback.class
        ,fallbackFactory = TemplateServiceFallbackFactory.class
        )//value 服务名称 ， path restController的前缀。此接口应该是由服务提供方维护，专门抽成一个jar包。fallback降级策略。fallbackFactory降级策略，还能输出错误信息。两者选其一
public interface TemplateService {

    // 读取优惠券
    @GetMapping("/getTemplate")
    CouponTemplateInfo getTemplate(@RequestParam("id") Long id);

    // 批量获取
    @GetMapping("/getBatch")
    Map<Long, CouponTemplateInfo> getTemplateInBatch(@RequestParam("ids") Collection<Long> ids);

}
