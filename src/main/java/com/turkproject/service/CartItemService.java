package com.turkproject.service;

import com.turkproject.model.ApplicationUser;
import com.turkproject.model.CartItem;
import com.turkproject.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> getAllCartItems() { return cartItemRepository.findAll(); }

    public List<CartItem> getCartItemsByUser(ApplicationUser user) {
        return cartItemRepository.findByUser(user);
    }



}
