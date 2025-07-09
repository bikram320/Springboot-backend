package com.example.spring_api_starter.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts", schema = "store_api")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "created_date" , insertable = false, updatable = false)
    private LocalDate createdDate;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.MERGE ,orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public CartItem getItem(long productId) {
        return getItems().stream()
                .filter(items -> items.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product) {
        var cartItem =getItem(product.getId());
        if(cartItem !=null){
            cartItem.setQuantity(cartItem.getQuantity()+1);
        }else {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setCart(this);
            items.add(cartItem);

        }
        return cartItem;
    }

    public void removeItem(long productId) {
        var cartItem =getItem(productId);
        if(cartItem !=null){
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

}