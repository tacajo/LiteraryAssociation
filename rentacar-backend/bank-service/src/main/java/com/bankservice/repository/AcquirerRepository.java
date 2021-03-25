package com.bankservice.repository;

import com.bankservice.model.Acquirer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcquirerRepository extends JpaRepository<Acquirer, Long> {
    Acquirer findByLu(String lu);
}
