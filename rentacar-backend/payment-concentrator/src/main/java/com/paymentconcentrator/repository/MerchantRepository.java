package com.paymentconcentrator.repository;

import com.paymentconcentrator.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Merchant findByLu(String lu);
}
