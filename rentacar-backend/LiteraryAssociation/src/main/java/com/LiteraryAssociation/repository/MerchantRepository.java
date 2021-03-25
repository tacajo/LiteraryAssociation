package com.LiteraryAssociation.repository;

import com.LiteraryAssociation.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
}
