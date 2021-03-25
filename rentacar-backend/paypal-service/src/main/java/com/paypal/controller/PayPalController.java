package com.paypal.controller;

import com.paypal.PayPalClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4201")
@RequestMapping(value = "/paypal")
public class PayPalController {

    private final PayPalClient payPalClient;


    @Autowired
    PayPalController(PayPalClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam("sum") String sum) {
        return payPalClient.createPayment(sum);
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(HttpServletRequest request){
        return payPalClient.completePayment(request);
    }
}
