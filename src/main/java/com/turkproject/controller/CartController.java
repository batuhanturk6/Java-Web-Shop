package com.turkproject.controller;


import com.turkproject.global.GlobalData;
import com.turkproject.model.ApplicationUser;
import com.turkproject.model.CartItem;
import com.turkproject.model.PaymentMethod;
import com.turkproject.model.Product;
import com.turkproject.repository.CartItemRepository;
import com.turkproject.repository.UserRepository;
import com.turkproject.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Date;
import java.util.Random;

@Controller
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id, HttpServletRequest request) {
        System.out.println(request.getParameter("displayQuantity"));

        int quantity = Integer.parseInt(request.getParameter("displayQuantity"));

        System.out.println(quantity);

        Product product = productService.getProductById(id).get();
        CartItem cartItem = new CartItem(product, quantity);

        boolean itemExist = false;
        for(CartItem item : GlobalData.cart) {
            if (item.getProduct().getId() == product.getId()) {
                itemExist = true;
                item.setQuantity(item.getQuantity() + quantity);
                break;
            }
        }

        if (!itemExist) {
            GlobalData.cart.add(cartItem);
        }

        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String cart(Model model, @RequestParam(required = false) Integer updateIndex, @RequestParam(required = false) Integer updateQuantity) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum());
        model.addAttribute("cart", GlobalData.cart);

        if (updateIndex != null && updateQuantity != null && updateIndex >= 0 && updateQuantity < GlobalData.cart.size()) {
            CartItem cartItem = GlobalData.cart.get(updateIndex);
            cartItem.setQuantity(updateQuantity);
        }

        return "cart";
    }

    @PostMapping("/cart/updateItem/{index}")
    public String updateItem(@PathVariable int index, @RequestParam("displayQuantity") int quantity) {
        CartItem item = GlobalData.cart.get(index);

        System.out.println(quantity);
        item.setQuantity(quantity);
        return "redirect:/cart";
    }

    @PostMapping("/cart/removeItem/{index}")
    public String removeItem(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("total", GlobalData.cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum());
        return "checkout";
    }

    @GetMapping("/ordered/{paymentMethod}")
    public String ordered(Model model, @PathVariable PaymentMethod paymentMethod, Principal principal) {
        ApplicationUser user = userRepository.findByUsername(principal.getName());
        Date date = new Date();

        for(CartItem cartItem : GlobalData.cart) {
            cartItem.setUser(user);
            cartItem.setDate(date);
            cartItem.setPaymentMethod(paymentMethod);

            cartItemRepository.save(cartItem);
        }

        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("total", GlobalData.cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum());
        model.addAttribute("result", generateNumber());

        return "orderPlaced";
    }

    private String generateNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(7);

        for (int i = 0; i < 7; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }

}