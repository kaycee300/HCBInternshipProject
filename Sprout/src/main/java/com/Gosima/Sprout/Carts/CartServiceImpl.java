package com.Gosima.Sprout.Carts;


import com.Gosima.Sprout.Products.Product;
import com.Gosima.Sprout.Products.ProductRepository;
import com.Gosima.Sprout.User.Account;
import com.Gosima.Sprout.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart getOrCreateCartForUser(Long userId) {
        Account user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(user);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getOrCreateCartForUser(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            cart.getItems().add(new CartItem(cart, product, quantity));
        }

        cart.updateTotalPrice();
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart = getOrCreateCartForUser(userId);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cart.updateTotalPrice();
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getOrCreateCartForUser(userId);
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));

        cart.updateTotalPrice();
        return cartRepository.save(cart);
    }

    @Override
    public Cart clearCart(Long userId) {
        Cart cart = getOrCreateCartForUser(userId);
        cart.getItems().clear();
        cart.updateTotalPrice();
        return cartRepository.save(cart);
    }
}
