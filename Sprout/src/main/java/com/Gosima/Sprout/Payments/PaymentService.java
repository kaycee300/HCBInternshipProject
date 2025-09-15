package com.Gosima.Sprout.Payments;



import com.Gosima.Sprout.Carts.Cart;
import com.Gosima.Sprout.Carts.CartItem;
import com.Gosima.Sprout.Carts.CartRepository;
import com.Gosima.Sprout.Payments.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
//    private final CartItem cartItem;
    private final CartRepository cartRepository;
    private final SecureRandom random = new SecureRandom();

    private final List<String> banks = List.of(
            "First Bank", "GTBank", "Access Bank", "UBA", "Zenith Bank"
    );

    public PaymentService(PaymentRepository paymentRepository,  CartRepository cartRepository) {
        this.paymentRepository = paymentRepository;
//        this.cartItem = cartItem;
        this.cartRepository = cartRepository;
    }

    private double getCartTotal(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).get();
        return cart.updateTotalPrice();
    }

    public PaymentResponse processCardPayment(Long userId, CardPaymentRequest request) {
        double cartTotal = getCartTotal(userId);

        Payment payment = new Payment(
                "CARD",
                cartTotal,
                "SUCCESS",
                "Card ending with " + request.getCardNumber().substring(request.getCardNumber().length() - 4)
        );
        paymentRepository.save(payment);

        return new PaymentResponse("Card payment successful", cartTotal, payment.getStatus());
    }

    public PaymentResponse processTransferPayment(Long userId) {
        double cartTotal = getCartTotal(userId);

        String accountNumber = String.format("%010d", random.nextInt(1_000_000_000));
        String bankName = banks.get(random.nextInt(banks.size()));
        String details = bankName + " - " + accountNumber;

        Payment payment = new Payment("TRANSFER", cartTotal, "PENDING", details);
        paymentRepository.save(payment);

        return new PaymentResponse("Transfer to account: " + details, cartTotal, payment.getStatus());
    }

    public PaymentResponse confirmTransfer(Long paymentId) {
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);

        if (optionalPayment.isEmpty()) {
            return new PaymentResponse("Payment not found", 0, "FAILED");
        }

        Payment payment = optionalPayment.get();
        if (!"TRANSFER".equals(payment.getMethod())) {
            return new PaymentResponse("Not a transfer payment", payment.getAmount(), "FAILED");
        }

        payment.setStatus("SUCCESS");
        paymentRepository.save(payment);

        return new PaymentResponse(
                "Transfer confirmed for " + payment.getDetails(),
                payment.getAmount(),
                payment.getStatus()
                );}
}





















//import com.Gosima.Sprout.Carts.CartItemRepository;
//import com.Gosima.Sprout.Carts.CartRepository;
//import org.springframework.stereotype.Service;
//
//import java.security.SecureRandom;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class PaymentService {
//
//    private final PaymentRepository paymentRepository;
//    private final CartRepository cartRepository;
//
//    private final SecureRandom random = new SecureRandom();
//
//    private final List<String> banks = List.of(
//            "First Bank", "GTBank", "Access Bank", "UBA", "Zenith Bank"
//    );
//
//    public PaymentService(PaymentRepository paymentRepository, CartRepository cartRepository) {
//        this.paymentRepository = paymentRepository;
//        this.cartRepository = cartRepository;
//
//    }
//
//    private double getCartTotal(Long id) {
//        return cartRepository.findById(id)
//                .stream()
//                .mapToDouble(item -> item.getPrice() * item.getQuantity())
//                .sum();
//    }
//
//    public PaymentResponse processCardPayment(Long userId, CardPaymentRequest request) {
//        double cartTotal = getCartTotal(userId);
//
//        Payment payment = new Payment(
//                "CARD",
//                cartTotal,
//                "SUCCESS",
//                "Card ending with " + request.getCardNumber().substring(request.getCardNumber().length() - 4)
//        );
//        paymentRepository.save(payment);
//
//        return new PaymentResponse("Card payment successful", cartTotal, payment.getStatus());
//    }
//
//    public PaymentResponse processTransferPayment(Long userId) {
//        double cartTotal = getCartTotal(userId);
//
//        String accountNumber = String.format("%010d", random.nextInt(1_000_000_000));
//        String bankName = banks.get(random.nextInt(banks.size()));
//        String details = bankName + " - " + accountNumber;
//
//        Payment payment = new Payment("TRANSFER", cartTotal, "PENDING", details);
//        paymentRepository.save(payment);
//
//        return new PaymentResponse("Transfer to account: " + details, cartTotal, payment.getStatus());
//    }
//
//    public PaymentResponse confirmTransfer(Long paymentId) {
//        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
//
//        if (optionalPayment.isEmpty()) {
//            return new PaymentResponse("Payment not found", 0, "FAILED");
//        }
//
//        Payment payment = optionalPayment.get();
//        if (!"TRANSFER".equals(payment.getMethod())) {
//            return new PaymentResponse("Not a transfer payment", payment.getAmount(), "FAILED");
//        }
//
//        payment.setStatus("SUCCESS");
//        paymentRepository.save(payment);
//
//        return new PaymentResponse(
//                "Transfer confirmed for " + payment.getDetails(),
//                payment.getAmount(),
//                payment.getStatus()
//                );
//}
