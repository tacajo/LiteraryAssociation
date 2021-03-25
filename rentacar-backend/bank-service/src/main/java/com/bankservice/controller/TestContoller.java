package com.bankservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestContoller {

    @GetMapping(value = "/test")
    public void test() {
        System.out.println("Poslat zahtev");
    }

}
