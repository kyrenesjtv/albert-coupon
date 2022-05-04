package com.github.kyrenesjtv.coupon.customer.feign.fallback;

import com.github.kyrenesjtv.coupon.customer.feign.TemplateService;
import com.github.kyrenesjtv.coupon.template.api.beans.CouponTemplateInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Slf4j
@Component
public class TemplateServiceFallback implements TemplateService {

    @Override
    public CouponTemplateInfo getTemplate(Long id) {
        log.info("fallback getTemplate");
        return null;
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInBatch(Collection<Long> ids) {
        log.info("fallback getTemplateInBatch");
        return null;
    }
}
