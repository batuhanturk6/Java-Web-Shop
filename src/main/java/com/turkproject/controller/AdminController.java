package com.turkproject.controller;


import com.turkproject.dto.ProductDTO;
import com.turkproject.model.CartItem;
import com.turkproject.model.Category;
import com.turkproject.model.Product;
import com.turkproject.service.CartItemService;
import com.turkproject.service.CategoryService;
import com.turkproject.service.LogsService;
import com.turkproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    LogsService logsService;

    @Autowired
    CartItemService cartItemService;

    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    @GetMapping()
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "adminCategories";
    }

    @GetMapping("/categories/add")
    public String categoriesAdd(Model model) {
        model.addAttribute("category", new Category());
        return "adminCategoriesAdd";
    }

    @PostMapping("/categories/add")
    public String postCategoriesAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(id);

        if (categoryOptional.isPresent()) {
            model.addAttribute("category", categoryOptional.get());
            return "adminCategoriesAdd";
        } else
            return "404";
    }

    @GetMapping("/products")
    public String adminProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "adminProducts";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "adminProductsAdd";
    }

    @PostMapping("/products/add")
    public String postAddProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName") String imageName)  throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());

        String imageUUID;
        if(!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileName = Paths.get(uploadDir, imageUUID);
            Files.write(fileName, file.getBytes());
        } else {
            imageUUID = imageName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/products";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/product/update/{id}")
    public String updtateProduct(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategoryId((product.getCategory()).getId());
        productDTO.setImageName(product.getImageName());

        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);

        return "adminProductsAdd";
    }

    @GetMapping("/logs")
    public String showLogs(Model model) {
        model.addAttribute("logs", logsService.getAllLogs());
        return "adminLogs";
    }

    @GetMapping("/transactions")
    public String transactions(Model model) {
        List<CartItem> transactions = cartItemService.getAllCartItems();

        model.addAttribute("transactions", transactions);

        return "adminTransactions";
    }

}