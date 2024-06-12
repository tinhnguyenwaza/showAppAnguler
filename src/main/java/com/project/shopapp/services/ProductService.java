package com.project.shopapp.services;

import com.project.shopapp.dtos.ProductDTO;
import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.exceptions.InvalidParamExceoption;
import com.project.shopapp.models.Category;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.repositories.CategoryRepository;
import com.project.shopapp.repositories.ProductDetailRepository;
import com.project.shopapp.repositories.ProductImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{
    private final ProductDetailRepository productDetailRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    @Override
    public Product createProduct(ProductDTO productDTO) {
        Product newProduct = new Product();
        try {
            Category exisingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow( () -> new DataNotFoundException("Cannot find category with id " +
                            productDTO.getCategoryId()));

            newProduct = Product.builder()
                    .name(productDTO.getName())
                    .price(productDTO.getPrice())
                    .url(productDTO.getThumbnail())
                    .category(exisingCategory)
                    .build();
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        return productDetailRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        return productDetailRepository.findById(productId)
                .orElseThrow( () -> new DataNotFoundException("Cannot find product with id "));
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productDetailRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long Id, ProductDTO productDTO) throws Exception{
        Product existingProduct = getProductById(Id);
        if(existingProduct != null){
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow( () -> new DataNotFoundException("Cannot find product with id "));
            //copy các thuộc tiính từ DTO --> product
            //Có thể sử dụng ModelManager
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setUrl(productDTO.getThumbnail());
            return productDetailRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
     Optional<Product> optionalProduct = productDetailRepository.findById(productId);
        optionalProduct.ifPresent(productDetailRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return productDetailRepository.existsByname(name);
    }
    @Override
    public ProductImage createProductImage(
            Long productId, ProductImageDTO productImageDTO) throws Exception{
        Product existingProduct = productDetailRepository.findById(productImageDTO.getProductId())
                .orElseThrow( () -> new DataNotFoundException("Cannot find product with id "));

        ProductImage newProductImage = ProductImage.builder()
                .productId(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();
        // KO cho insrt quá 5 ảnh
        int size = productImageRepository.finByProductId(productId).size();
        if(size >= 5){
            throw new InvalidParamExceoption("Number of images must be <= 5");
        }
        return productImageRepository.save(newProductImage);
    };
}
