package com.example.demo.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Setter @Getter
public class Book extends Item {

    private String author;
    private String isbn;
}
