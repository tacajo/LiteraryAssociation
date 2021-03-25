package com.bitcoinservice.dto;

public class PaymentResponse {

    private String details;

    private Long idPayment;

    public String getDetails() {
        return details;
    }

    public Long getIdPayment() {
        return idPayment;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setIdPayment(Long idPayment) {
        this.idPayment = idPayment;
    }

    public PaymentResponse(String details, Long idPayment) {
        this.details = details;
        this.idPayment = idPayment;
    }

    public PaymentResponse() {
    }
}
