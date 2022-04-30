package com.github.kyrenesjtv.coupon.calculation.service;

import com.github.kyrenesjtv.coupon.calculation.api.beans.ShoppingCart;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationOrder;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface CouponCalculationService {

    ShoppingCart calculateOrderPrice(@RequestBody ShoppingCart cart);

    SimulationResponse simulateOrder(@RequestBody SimulationOrder cart);
}
