package com.namta.ecom.services.admin.product;

import com.namta.ecom.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

    ProductDto addProduct(ProductDto productDto);

    List<ProductDto> getAllProduct();

    List<ProductDto> getAllProductByName(String name);

    boolean deleteProduct(Long id);

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId,ProductDto productDto) throws IOException;
}
