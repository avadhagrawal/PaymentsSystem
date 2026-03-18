package org.example.dto;

public record PaymentRequest (
    String firstName,
    String lastName,
    String zipCode,
    String cardNumber
) {}