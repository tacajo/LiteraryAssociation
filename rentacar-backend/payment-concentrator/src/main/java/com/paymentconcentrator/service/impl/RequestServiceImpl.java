package com.paymentconcentrator.service.impl;

import com.paymentconcentrator.model.Request;
import com.paymentconcentrator.repository.RequestRepository;
import com.paymentconcentrator.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public String findUrl(String orderId, boolean success) {
        Request request = requestRepository.findByMerchantOrderId(orderId);
        if(success) {
            return request.getSuccess_url();
        } else {
            return request.getError_url();
        }
    }
}
