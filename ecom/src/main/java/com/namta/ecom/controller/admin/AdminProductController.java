package com.namta.ecom.controller.admin;

import com.namta.ecom.dto.ProductDto;
import com.namta.ecom.services.admin.product.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {
    public final AdminProductService adminProductService;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) throws IOException {
        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = adminProductService.getAllProduct();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
        List<ProductDto> productDtos = adminProductService.getAllProductByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        boolean deleted = adminProductService.deleteProduct(productId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId){
        ProductDto productDto = adminProductService.getProductById(productId);
        if(productDto!=null){
            return ResponseEntity.ok(productDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long productId,@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto updatedProductDto = adminProductService.updateProduct(productId,productDto);
        if(updatedProductDto!=null){
            return ResponseEntity.ok(updatedProductDto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
