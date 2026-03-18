package org.example.service;

import org.example.dto.PaymentRequest;
import org.example.entity.Payment;
import org.example.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private WebhookDispatcher dispatcher;

    public Payment createPayment(PaymentRequest request) {

        Payment payment = new Payment();

        payment.setFirstName(request.firstName());
        payment.setLastName(request.lastName());
        payment.setZipCode(request.zipCode());

        payment.setCardNumber(encrypt(request.cardNumber()));

        Payment savedPayment = paymentRepository.save(payment);

        dispatcher.dispatch(savedPayment);

        return savedPayment;
    }

    private String encrypt(String card) {
        return Base64.getEncoder().encodeToString(card.getBytes());
    }
}