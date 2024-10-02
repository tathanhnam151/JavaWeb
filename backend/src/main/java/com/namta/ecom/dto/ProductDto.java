package com.namta.ecom.dto;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ProductDto {
    private long id;

    private String name;

    private Long price;

    private String description;

    private long categoryId;

    private String categoryName;
}
