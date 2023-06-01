package com.example.ecommercial.domain.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookEntity extends BaseEntity{
    private String title;
    private String author;
    private String price;
}
