package com.paypal.repository;

import com.paypal.model.SubscriberData;
import com.paypal.model.SubscriptionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionDetailsRepository extends JpaRepository<SubscriptionDetails, Long> {
}
