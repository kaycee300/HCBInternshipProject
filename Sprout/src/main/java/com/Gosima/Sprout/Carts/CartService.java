package com.Gosima.Sprout.Carts;



public interface CartService {
    Cart getOrCreateCartForUser(Long userId);
    Cart addProductToCart(Long userId, Long productId, int quantity);
    Cart removeProductFromCart(Long userId, Long productId);
    Cart updateQuantity(Long userId, Long productId, int quantity);
    Cart clearCart(Long userId);
}



































//
//
//import com.Gosima.Sprout.Products.Product;
//import com.Gosima.Sprout.Products.ProductRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class CartService {
//
//    private final CartRepository cartRepository;
//    private final ProductRepository productRepository;
//
//    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
//        this.cartRepository = cartRepository;
//        this.productRepository = productRepository;
//    }
//
//    // Get user's cart (create if doesn't exist)
//    public Cart getCart(Long cartId) {
//        return cartRepository.findById(cartId)
//                .orElseGet(() -> cartRepository.save(new Cart()));
//    }
//
//    // Add product with increment logic
//    public Cart addProductToCart(Long cartId, Long productId, int quantity) {
//        Cart cart = getCart(cartId);
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
//
//        boolean found = false;
//        for (CartItem item : cart.getItems()) {
//            if (item.getProduct().getId().equals(productId)) {
//                item.setQuantity(item.getQuantity() + quantity);
//                found = true;
//                break;
//            }
//        }
//
//        if (!found) {
//            CartItem newItem = new CartItem(product, quantity);
//            cart.getItems().add(newItem);
//        }
//
//        return cartRepository.save(cart);
//    }
//
//    // Decrease quantity or remove if zero
//    public Cart decreaseProductQuantity(Long cartId, Long productId, int quantity) {
//        Cart cart = getCart(cartId);
//        cart.getItems().removeIf(item -> {
//            if (item.getProduct().getId().equals(productId)) {
//                int newQty = item.getQuantity() - quantity;
//                if (newQty > 0) {
//                    item.setQuantity(newQty);
//                    return false;
//                }
//                return true; // remove if quantity <= 0
//            }
//            return false;
//        });
//
//        return cartRepository.save(cart);
//    }
//
//    // Remove product completely
//    public Cart removeProductFromCart(Long cartId, Long productId) {
//        Cart cart = getCart(cartId);
//        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
//        return cartRepository.save(cart);
//    }
//
//    // Get total cart price
//    public double getCartTotal(Long cartId) {
//        Cart cart = getCart(cartId);
//        return cart.getCartTotal();
//    }
//}
