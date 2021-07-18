package com.example.demo.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter @Setter
public class ItemForm {
    private Long id;

    @NotEmpty(message = "상품 이름 적어야합니다.")
    private String name;

    @Positive(message = "상품 가격은 0원일수 없습니다.")
    private int price;
    @Positive(message = "상품 가격은 0원일수 없습니다.")
    private int stockQuantity;

    private String author;
    private String isbn;
}
