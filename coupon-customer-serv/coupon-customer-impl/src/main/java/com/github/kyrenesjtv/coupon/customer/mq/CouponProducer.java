package com.github.kyrenesjtv.coupon.customer.mq;

import com.github.kyrenesjtv.coupon.customer.api.beans.RequestCoupon;
import com.github.kyrenesjtv.coupon.customer.constant.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CouponProducer {

    @Autowired
    private StreamBridge streamBridge;

    public void sendCoupon(RequestCoupon coupon) {
        log.info("sent: {}", coupon);
        streamBridge.send(MQConstant.ADD_COUPON_EVENT, coupon);
    }

    // 使用延迟消息发送
    public void sendCouponInDelay(RequestCoupon coupon) {
        log.info("sent: {}", coupon);
        streamBridge.send(MQConstant.ADD_COUPON_DELAY_EVENT,
                MessageBuilder.withPayload(coupon)
                        .setHeader("x-delay", 10 * 1000)
                        .build());
    }

    public void deleteCoupon(Long userId, Long couponId) {
        log.info("sent delete coupon event: userId={}, couponId={}", userId, couponId);
        streamBridge.send(MQConstant.DELETE_COUPON_EVENT, userId + "," + couponId);
    }

}
