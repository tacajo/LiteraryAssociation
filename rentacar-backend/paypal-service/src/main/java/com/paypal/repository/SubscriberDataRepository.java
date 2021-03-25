package com.paypal.repository;

import com.paypal.model.SubscriberData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberDataRepository extends JpaRepository<SubscriberData, Long> {
}
