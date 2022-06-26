package com.github.kyrenesjtv.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutesConfiguration {

    @Bean
    public RouteLocator declare(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/gateway/coupon-customer/**")
                        .filters(f -> f.stripPrefix(1))//过滤path中的前缀。1的话就是变成/coupon-customer/**。注意，最后的路径一定要跟服务的路径一样，不然会404
                        .uri("lb://coupon-customer-serv")//将请求转给nacos中注册的coupon-customer-serv服务
                ).route(route -> route
                        .order(1)
                        .path("/gateway/template/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://coupon-template-serv")
                ).route(route -> route
                        .path("/gateway/calculator/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://coupon-calculation-serv")
                )
                .route(route -> route
                        .path("/test/baidu")
                        .uri("https://www.baidu.com/"))
                .build();
    }
}