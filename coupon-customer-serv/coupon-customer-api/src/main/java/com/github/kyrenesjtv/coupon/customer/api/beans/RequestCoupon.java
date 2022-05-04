package com.github.kyrenesjtv.coupon.customer.api.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCoupon {

    // 用户领券
    @NotNull
    private Long userId;

    // 券模板ID
    @NotNull
    private Long couponTemplateId;

    // Loadbalancer - 用作测试流量打标。trafficVersion的值需要与nacos服务列表中(被调用方)所配置的值一致，才能将请求打到对应的实例中
    private String trafficVersion;

}
