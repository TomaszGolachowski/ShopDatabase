package com.app.tGolachowski.dto;

import com.app.tGolachowski.entity.GuaranteeComponent;
import com.app.tGolachowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private ProducerDto producerDto;
    private CategoryDto categoryDto;
    private Set<GuaranteeComponent> guaranteeComponentSet;

    public ProductDto(String name, BigDecimal price, ProducerDto producerDto, CategoryDto categoryDto,Set<GuaranteeComponent> guaranteeComponents) {
        setName(name);
        setPrice(price);
        this.producerDto = producerDto;
        this.categoryDto = categoryDto;
        this.guaranteeComponentSet = guaranteeComponents;
    }

    public void setName(String name) {
        if (name.matches("[A-Z ]*"))
            this.name = name;
        else {
            throw new MyException("PRODUCT: WRONG INPUT CAPITAL LETTERS OR SPACE ONLY",LocalDateTime.now());
        }
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) > 0) {
            this.price = price;
        } else {
            throw new MyException("PRODUCT: WRONG DIGITS ONLY",LocalDateTime.now());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDto)) return false;

        ProductDto that = (ProductDto) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return getPrice() != null ? getPrice().equals(that.getPrice()) : that.getPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        return result;
    }
}
