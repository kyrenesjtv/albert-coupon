package com.github.kyrenesjtv.coupon.calculation.template;


import com.github.kyrenesjtv.coupon.calculation.api.beans.ShoppingCart;

public interface RuleTemplate {

    // 计算优惠券
    ShoppingCart calculate(ShoppingCart settlement);
}
