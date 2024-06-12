package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value = 1,message = "orderId money must be >= 1")
    private Long orderId;

    @JsonProperty("product_id")
    @Min(value = 1,message = "product money must be >= 1")
    private Long productId;

    @Min(value = 1,message = "price money must be >= 1")
    private Long price;

    @JsonProperty("number_of_products")
    @Min(value = 1,message = "numberOfProduct money must be >= 1")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value = 1,message = "totalMoney money must be >= 1")
    private int totalMoney;

    private String color;
}
