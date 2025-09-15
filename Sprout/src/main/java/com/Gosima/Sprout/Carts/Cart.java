package com.Gosima.Sprout.Carts;

//import com.Gosima.Sprout.User.Account;
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Cart {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//    public Cart() {}
//
//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }
//
//    public Cart(Account account) {
//        this.account = account;
//    }
//
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private Account account;
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "cart_id")
//    private List<CartItem> items = new ArrayList<>();
//
//
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public List<CartItem> getItems() { return items; }
//    public void setItems(List<CartItem> items) { this.items = items; }
//
//    public double getCartTotal() {
//        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
//    }
//
//
//
//
//}

import com.Gosima.Sprout.User.Account;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Account user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private double totalPrice = 0.0;

    public Cart() {}

    public Cart(Account user) {
        this.user = user;
    }

    public double updateTotalPrice() {
        totalPrice = items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        return totalPrice;
    }

    // getters and setters
    public Long getId() { return id; }

    public Account getUser() { return user; }

    public void setUser(Account user) { this.user= user; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }
    public double getTotalPrice() { return totalPrice; }


}
