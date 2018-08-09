package com.app.tGolachowski.dto;

import com.app.tGolachowski.entity.EPayment;
import com.app.tGolachowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerOrderDto {

    private Long id;
    private LocalDate date;
    private BigDecimal discount;
    private int quantity;
    private CustomerDto customerDto;
    private Set<EPayment> ePayment;
    private ProductDto productDto;

    public CustomerOrderDto(LocalDate date, BigDecimal discount, int quantity, CustomerDto customerDto, Set<EPayment> ePayment, ProductDto productDto) {
        this.date = date;
        this.discount = discount;
        this.quantity = quantity;
        this.customerDto = customerDto;
        setePayment(ePayment);
        this.productDto = productDto;
    }

    public void setePayment(Set<EPayment> ePayment) {
        if (ePayment.size() == 1) {
            this.ePayment = ePayment;
        } else {
            throw new MyException("CUSTOMER_ORDER: WRONG PAYMENT TABLE SIZE", LocalDateTime.now());
        }
    }
}
