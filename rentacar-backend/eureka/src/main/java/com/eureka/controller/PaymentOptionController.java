package com.eureka.controller;

import com.eureka.PaymentOptionDTO;
import com.eureka.model.PaymentWay;
import com.eureka.repository.PaymentWayRepository;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PaymentOptionController {

    @Autowired
    private PaymentWayRepository paymentWayRepository;

    @GetMapping(value = "/services")
    public PaymentOptionDTO getServices() {
        PaymentOptionDTO dto = new PaymentOptionDTO();
        PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
        Applications applications = registry.getApplications();

        applications.getRegisteredApplications().forEach((registeredApplication) -> {
            registeredApplication.getInstances().forEach((instance) -> {
                if(!instance.getAppName().equals("ZUUL")  && !instance.getAppName().equals("EUREKA-SERVICEREGISTRY") &&
                        !instance.getAppName().equals("PAYMENT-CONCENTRATOR")  &&
                        !instance.getAppName().equals("PAYMENT-CARD-CENTAR") &&
                        !instance.getAppName().equals("AUTH")) {
                    System.out.println(instance.getAppName());
                    dto.getOption().add(instance.getAppName());
                    if(paymentWayRepository.findByName(instance.getAppName()) == null) {
                        PaymentWay paymentWay = new PaymentWay();
                        paymentWay.setName(instance.getAppName());
                        paymentWayRepository.save(paymentWay);
                    }
                }
            });
        });

        return dto;
    }
}
