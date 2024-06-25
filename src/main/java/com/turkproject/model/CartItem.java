package com.turkproject.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;

    private Date date;

    private PaymentMethod paymentMethod;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
