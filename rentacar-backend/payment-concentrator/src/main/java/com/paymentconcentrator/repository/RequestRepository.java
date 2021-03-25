package com.paymentconcentrator.repository;

import com.paymentconcentrator.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findByMerchantOrderId(String merchantOrderId);
}
