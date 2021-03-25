package com.bitcoinservice.service;

import com.bitcoinservice.dto.BitcoinDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface BitcoinService {

    ResponseEntity<?> pay(String amount, String uuid);

    ResponseEntity<?> editTransaction(boolean flag, Long id);
}
