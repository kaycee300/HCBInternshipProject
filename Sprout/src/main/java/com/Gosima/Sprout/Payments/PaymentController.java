package com.Gosima.Sprout.Payments;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/card/{userId}")
    public PaymentResponse payWithCard(
            @PathVariable Long userId,
            @RequestBody CardPaymentRequest request) {
        return paymentService.processCardPayment(userId, request);
    }

    @PostMapping("/transfer/{userId}")
    public PaymentResponse payWithTransfer(@PathVariable Long userId) {
        return paymentService.processTransferPayment(userId);
    }

    @PostMapping("/transfer/confirm")
    public PaymentResponse confirmTransfer(@RequestBody TransferPaymentRequest request) {
        return paymentService.confirmTransfer(request.getPaymentId());
}
}