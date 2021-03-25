package com.bankservice.repository;

import com.bankservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByPan(String pan);
}
