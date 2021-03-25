package com.bankservice.repository;

import com.bankservice.model.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuerRepository extends JpaRepository<Issuer, Long> {
    Issuer findByUsername(String username);
}
