package com.namta.ecom.services.customer;

import com.namta.ecom.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> getAllProduct();

    List<ProductDto> getAllProductByName(String name);
}
