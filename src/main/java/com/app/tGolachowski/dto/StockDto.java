package com.app.tGolachowski.dto;

import com.app.tGolachowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class StockDto {
    private Long id;
    private ProductDto productDto;
    private ShopDto shopDto;
    private Integer quantity;

    public StockDto(ProductDto productDto, ShopDto shopDto, Integer quantity) {
        this.productDto = productDto;
        this.shopDto = shopDto;
        this.quantity = quantity;
    }

    public void setQuantity(Integer quantity) {
        if(quantity>0) {
            this.quantity = quantity;
        }
        else {
            throw new MyException("STOCK: QUANTITY HAS TO BE GREATER THAN 0",LocalDateTime.now());
        }
    }
}
