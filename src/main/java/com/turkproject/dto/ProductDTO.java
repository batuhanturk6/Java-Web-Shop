package com.turkproject.dto;


import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private double price;
    private String description;
    private String imageName;
    private int categoryId;

}
