package com.github.kyrenesjtv.coupon.customer.controller;

import com.github.kyrenesjtv.coupon.calculation.api.beans.ShoppingCart;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationOrder;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationResponse;
import com.github.kyrenesjtv.coupon.customer.api.beans.RequestCoupon;
import com.github.kyrenesjtv.coupon.customer.api.beans.SearchCoupon;
import com.github.kyrenesjtv.coupon.customer.dao.entity.Coupon;
import com.github.kyrenesjtv.coupon.customer.mq.CouponProducer;
import com.github.kyrenesjtv.coupon.customer.service.CouponCustomerService;
import com.github.kyrenesjtv.coupon.template.api.beans.CouponInfo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("coupon-customer")
@RefreshScope
public class CouponCustomerController {


    @Value("${disableCouponRequest:false}")
    private Boolean disableCoupon;

    @Autowired
    private CouponCustomerService customerService;

    @Autowired
    private CouponProducer couponProducer;

    @PostMapping("requestCoupon")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request) {
        if(disableCoupon){
            log.info("暂停领取优惠券");
            return null;
        }
        log.info("测试sleuth");
        return customerService.requestCoupon(request);
    }

    // 用户删除优惠券
    @DeleteMapping("deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") Long userId,
                                       @RequestParam("couponId") Long couponId) {
        customerService.deleteCoupon(userId, couponId);
    }


    @PostMapping("requestCouponEvent")
    public void requestCouponEvent(@Valid @RequestBody RequestCoupon request) {
        couponProducer.sendCoupon(request);
    }

    @PostMapping("requestCouponDelayEvent")
    public void requestCouponDelayedEvent(@Valid @RequestBody RequestCoupon request) {
        couponProducer.sendCouponInDelay(request);
    }
    // 用户删除优惠券
    @DeleteMapping("deleteCouponEvent")
    public void deleteCouponEvent(@RequestParam("userId") Long userId,
                                  @RequestParam("couponId") Long couponId) {
        couponProducer.deleteCoupon(userId, couponId);
    }
    // 用户删除优惠券
    @DeleteMapping("template")
    @GlobalTransactional(name = "coupon-customer-serv", rollbackFor = Exception.class)
    public void deleteTemplate(@RequestParam("templateId") Long templateId) {
        customerService.deleteCouponTemplate(templateId);
    }

    // 用户模拟计算每个优惠券的优惠价格
    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return customerService.simulateOrderPrice(order);
    }

    // ResponseEntity - 指定返回状态码 - 可以作为一个课后思考题
    @PostMapping("placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart info) {
        return customerService.placeOrder(info);
    }


    // 实现的时候最好封装一个search object类
    @PostMapping("findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return customerService.findCoupon(request);
    }

}
