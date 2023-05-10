package com.example.icare.controller;

import com.example.icare.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/payment")
public class PaymentController {
    private final PaymentService paymentService;
@Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
