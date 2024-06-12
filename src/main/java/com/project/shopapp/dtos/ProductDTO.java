package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
 @NotBlank(message = "Title is required")
 @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
 private String name;

 @Min(value = 0, message = "Price must be grater than or equal to 0")
 @Max(value = 10000000, message = "price must be less than or equal to 10,000,000")
 private Float price;

 private String thumbnail;
 private String description;

 //@NotNull(message = "Category ID is required")
 @JsonProperty("category_id")
 private Long categoryId;

 private List<MultipartFile> files;

}
