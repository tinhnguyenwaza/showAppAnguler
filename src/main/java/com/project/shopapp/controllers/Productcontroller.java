package com.project.shopapp.controllers;

import com.project.shopapp.dtos.ProductDTO;

import com.project.shopapp.dtos.ProductImageDTO;
import com.project.shopapp.models.Product;
import com.project.shopapp.models.ProductImage;
import com.project.shopapp.services.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class Productcontroller {
    private final IProductService productService;

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAllProducts(
            @Valid @ModelAttribute ProductDTO productDTO,
            //@RequestPart("file") MultipartFile file,
            BindingResult result
    ){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

           List<MultipartFile> files = productDTO.getFiles();
            // in case files == nul -need new Araylist
            Product newProduct = productService.createProduct(productDTO);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for(MultipartFile file: files){
                if(file != null){
                    if (file.getSize() == 0)continue;

                    if(file.getSize() > 10*1024*1024){// kich thuoc 10MB
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maxsimum size is 10MB");
                    }
                    String contentype = file.getContentType();
                    if(contentype == null || !contentype.startsWith("image/")){
                        return ResponseEntity.status(
                                HttpStatus.PAYLOAD_TOO_LARGE).body("File must be an image");
                    }
                    //save file and update in folder and DB
                    String fileName = storeFile(file);
                    // LƯU VÀO TABLE PRUDUCT
                    ProductImage productImage = productService.createProductImage(newProduct.getId(),
                            ProductImageDTO.builder()
                                    .imageUrl(fileName)
                                    .build());
                }
            }
            productService.createProduct(productDTO);


            return  ResponseEntity.ok("Product create seccesfully");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    private String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(),uniqueFileName);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @GetMapping("")
    public ResponseEntity<String> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("page") int limit
    ){
        return ResponseEntity.ok("getProduct here");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getAllProductsbyId(
            @PathVariable("id") String productId
    ){
        return ResponseEntity.status(HttpStatus.OK).body("Product successfully");
    }


}
