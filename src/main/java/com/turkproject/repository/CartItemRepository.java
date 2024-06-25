package com.turkproject.repository;

import com.turkproject.model.ApplicationUser;
import com.turkproject.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(ApplicationUser user);
}
