package com.github.kyrenesjtv.coupon.customer.dao;

import com.github.kyrenesjtv.coupon.customer.dao.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDao extends JpaRepository<Coupon, Long> {

    long countByUserIdAndTemplateId(Long userId, Long templateId);

//    void deleteCouponInBatch(Long templateId, CouponStatus inactive);
}
