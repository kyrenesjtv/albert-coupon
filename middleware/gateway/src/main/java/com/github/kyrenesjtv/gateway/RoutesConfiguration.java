package com.github.kyrenesjtv.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class RoutesConfiguration {


    @Autowired
    private KeyResolver hostAddrKeyResolver;

    @Autowired
    @Qualifier("customerRateLimiter")
    private RateLimiter customerRateLimiter;

    @Autowired
    @Qualifier("tempalteRateLimiter")
    private RateLimiter templateRateLimiter;


    @Bean
    public RouteLocator declare(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route
                        .path("/gateway/coupon-customer/**")
                        .filters(f -> f.stripPrefix(1))//过滤path中的前缀。1的话就是变成/coupon-customer/**。注意，最后的路径一定要跟服务的路径一样，不然会404
                        .uri("lb://coupon-customer-serv")//将请求转给nacos中注册的coupon-customer-serv服务
                ).route(route -> route
                        // 如果一个请求命中了多个路由，order越小的路由优先级越高
                        .order(1)
                        .path("/gateway/template/**")
                        .filters(f -> f.stripPrefix(1)
                                .requestRateLimiter(c-> {
                                            c.setKeyResolver(hostAddrKeyResolver);
                                            c.setRateLimiter(templateRateLimiter);//限流
                                            c.setStatusCode(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);//请求被限流了，就返回这个状态
                                        }
                                )
                        )
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