package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


public interface IProductService {
    public Product createProduct(ProductDTO productDTO);
    Product getProductById(Long Id) throws  Exception;
    Page<Product> getAllProducts(PageRequest pageRequest);
    Product updateProduct(Long Id, ProductDTO productDTO) throws Exception;
    void deleteProduct(Long Id);
    boolean existsByName(String name);
    public ProductImage createProductImage(
            Long productId, ProductImageDTO productImageDTO) throws Exception;
}
