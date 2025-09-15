package com.Gosima.Sprout.Payments;

public class PaymentResponse {
    private String message;
    private double amount;
    private String status;

    public PaymentResponse(String message, double amount, String status) {
        this.message = message;
        this.amount = amount;
        this.status = status;
    }

    public String getMessage() { return message; }
    public double getAmount() { return amount; }
    public String getStatus() { return status;}
}
