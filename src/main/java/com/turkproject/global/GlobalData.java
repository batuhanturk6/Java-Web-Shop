package com.turkproject.global;


import com.turkproject.model.CartItem;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<CartItem> cart;

    static {
        cart = new ArrayList<>();
    }
}