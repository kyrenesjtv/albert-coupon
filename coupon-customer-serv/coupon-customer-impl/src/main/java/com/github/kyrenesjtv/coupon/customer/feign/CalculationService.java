package com.github.kyrenesjtv.coupon.customer.feign;

import com.github.kyrenesjtv.coupon.calculation.api.beans.ShoppingCart;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationOrder;
import com.github.kyrenesjtv.coupon.calculation.api.beans.SimulationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "coupon-calculation-serv", path = "/calculator")//value 服务名称 ， path restController的前缀。此接口应该是由服务提供方维护，专门抽成一个jar包
public interface CalculationService {

    // 订单结算
    @PostMapping("/checkout")
    ShoppingCart checkout(ShoppingCart settlement);

    // 优惠券试算
    @PostMapping("/simulate")
    SimulationResponse simulate(SimulationOrder simulator);
}