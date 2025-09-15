package com.Gosima.Sprout.Carts;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}/add/{productId}")
    public Cart addProduct(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity) {
        return cartService.addProductToCart(userId, productId, quantity);
    }

    @PostMapping("/{userId}/remove/{productId}")
    public Cart removeProduct(@PathVariable Long userId, @PathVariable Long productId) {
        return cartService.removeProductFromCart(userId, productId);
    }

    @PostMapping("/{userId}/update/{productId}")
    public Cart updateQuantity(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity) {
        return cartService.updateQuantity(userId, productId, quantity);
    }

    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable Long userId) {
        return cartService.getOrCreateCartForUser(userId);
    }

    @PostMapping("/{userId}/clear")
    public Cart clearCart(@PathVariable Long userId) {
        return cartService.clearCart(userId);
    }
}


































//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartController {
//
//    private final CartService cartService;
//
//    public CartController(CartService cartService) {
//        this.cartService = cartService;
//    }
//
//    // Get Cart details
//    @GetMapping("/{cartId}")
//    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
//        return ResponseEntity.ok(cartService.getCart(cartId));
//    }
//
//    // Add product to cart (increments if already exists)
//    @PostMapping("/{cartId}/add/{productId}")
//    public ResponseEntity<Cart> addProductToCart(
//            @PathVariable Long cartId,
//            @PathVariable Long productId,
//            @RequestParam(defaultValue = "1") int quantity) {
//
//        return ResponseEntity.ok(cartService.addProductToCart(cartId, productId, quantity));
//    }
//
//    // Decrease product quantity or remove if zero
//    @PostMapping("/{cartId}/decrease/{productId}")
//    public ResponseEntity<Cart> decreaseProductQuantity(
//            @PathVariable Long cartId,
//            @PathVariable Long productId,
//            @RequestParam(defaultValue = "1") int quantity) {
//
//        return ResponseEntity.ok(cartService.decreaseProductQuantity(cartId, productId, quantity));
//    }
//
//    // Remove product completely
//    @DeleteMapping("/{cartId}/remove/{productId}")
//    public ResponseEntity<Cart> removeProductFromCart(
//            @PathVariable Long cartId,
//            @PathVariable Long productId) {
//
//        return ResponseEntity.ok(cartService.removeProductFromCart(cartId, productId));
//    }
//
//    // Get cart total
//    @GetMapping("/{cartId}/total")
//    public ResponseEntity<Double> getCartTotal(@PathVariable Long cartId) {
//        return ResponseEntity.ok(cartService.getCartTotal(cartId));
//    }
//}
//
