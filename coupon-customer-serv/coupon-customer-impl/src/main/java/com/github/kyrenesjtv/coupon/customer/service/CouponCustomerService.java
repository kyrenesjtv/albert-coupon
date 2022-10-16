package com.github.kyrenesjtv.coupon.customer.service;


import com.github.kyrenesjtv.coupon.calculation.api.beans.ShoppingCart;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationOrder;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationResponse;
import com.github.kyrenesjtv.coupon.customer.api.beans.RequestCoupon;
import com.github.kyrenesjtv.coupon.customer.api.beans.SearchCoupon;
import com.github.kyrenesjtv.coupon.customer.dao.entity.Coupon;
import com.github.kyrenesjtv.coupon.template.api.beans.CouponInfo;

import java.util.List;

// 用户对接服务
public interface CouponCustomerService {

    // 领券接口
    Coupon requestCoupon(RequestCoupon request);

    // 核销优惠券
    ShoppingCart placeOrder(ShoppingCart info);

    // 优惠券金额试算
    SimulationResponse simulateOrderPrice(SimulationOrder order);

    void deleteCoupon(Long userId, Long couponId);

    void deleteCouponTemplate(Long templateId);

    // 查询用户优惠券
    List<CouponInfo> findCoupon(SearchCoupon request);
}
