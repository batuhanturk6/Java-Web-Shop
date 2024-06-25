package com.turkproject.controller;


import com.turkproject.global.GlobalData;
import com.turkproject.model.ApplicationUser;
import com.turkproject.model.CartItem;
import com.turkproject.model.Product;
import com.turkproject.repository.UserRepository;
import com.turkproject.service.CartItemService;
import com.turkproject.service.CategoryService;
import com.turkproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    UserRepository userRepository;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(@RequestParam(required = false) String query, Model model) {
        List<Product> searchProducts;

        if (query != null && !query.isEmpty())
            searchProducts = productService.searchProductsByName(query);
        else
            searchProducts = productService.getAllProducts();

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", searchProducts);
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/clear")
    public String shopClearCart(Model model) {

        GlobalData.cart.clear();

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(@PathVariable int id, Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(@PathVariable int id, Model model) {
        model.addAttribute("product", productService.getProductById(id).get());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "viewProduct";
    }


    @GetMapping("/transactions")
    public String transactions(Model model, Principal principal) {
        ApplicationUser user = userRepository.findByUsername(principal.getName());
        List<CartItem> transactions = cartItemService.getCartItemsByUser(user);

        model.addAttribute("transactions", transactions);

        return "transactions";
    }


}