package com.github.kyrenesjtv.coupon.customer.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenfeignSentinelInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("sentinelSource", "coupon-customer-serv");
    }
}
