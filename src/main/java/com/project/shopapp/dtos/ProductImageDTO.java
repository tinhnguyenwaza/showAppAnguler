package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
    @Min(value = 1,message = "Product's ID must be >0")
    @JsonProperty("product_id")
    private Long productId;

    @Size(min =5 , max = 200, message = "Image's name")
    @Column(name = "image_url",length = 300)
    private String imageUrl;
}
