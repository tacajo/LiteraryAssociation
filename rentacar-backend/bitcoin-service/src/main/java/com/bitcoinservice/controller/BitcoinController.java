package com.bitcoinservice.controller;

import com.bitcoinservice.service.BitcoinService;
import com.bitcoinservice.wallet.BitcoinWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200" , "http://localhost:4201" })
@RestController
@RequestMapping(value = "/bitcoin", produces = MediaType.APPLICATION_JSON_VALUE)
public class BitcoinController {

    @Autowired
    private BitcoinWallet myWallet;

    @Autowired
    private BitcoinService bitcoinService;

    @PutMapping(value = "/{flag}/{id}")
    public ResponseEntity<?> editTransaction(@PathVariable("flag") boolean flag, @PathVariable("id") Long id){
        return bitcoinService.editTransaction(flag, id);
    }

    @PostMapping(value = "/{amount}/{uuid}")
    public ResponseEntity<?> pay(@PathVariable("amount") String amount, @PathVariable("uuid") String uuid) {
        return bitcoinService.pay(amount, uuid);
    }

    @PostMapping(value = "/send/{amount}/{address}/{uuid}")
    public ResponseEntity<?> send(@PathVariable("amount") String amount, @PathVariable("address") String address, @PathVariable("uuid") String uuid) {
        return myWallet.send(amount, address, uuid);
    }

    @GetMapping(value = "/test")
    public String test() {
        return "RADI!";
    }

}