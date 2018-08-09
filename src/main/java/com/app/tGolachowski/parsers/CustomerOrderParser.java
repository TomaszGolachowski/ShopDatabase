package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.CustomerDto;
import com.app.tGolachowski.dto.CustomerOrderDto;
import com.app.tGolachowski.dto.ProductDto;
import com.app.tGolachowski.entity.EPayment;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.service.MyService;
import com.app.tGolachowski.service.MyServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class CustomerOrderParser implements Parser<CustomerOrderDto> {
    private MyService myService = new MyServiceImpl();
    private Random rd = new Random();

    @Override
    public CustomerOrderDto parse(String line) {
        if (!Parser.isLineCorrect(line, RegularExpressions.CUSTOMER_ORDER_REGEX)) {
            throw new MyException("CUSTOMER ORDER PARSER: REGEX ERROR", LocalDateTime.now());
        }

        List<String> customerOrderData = Arrays.asList(line.split(";"));
        CustomerDto customerDto = myService.findCustomerByNameSurnameAndCountry(CustomerDto.builder()
                .name(customerOrderData.get(0))
                .surname(customerOrderData.get(1))
                .CountryDto(myService.findCountryByName(customerOrderData.get(2))).build());

        ProductDto productDto = myService.findProductByName(customerOrderData.get(3));

        Set<EPayment> ePaymentSet = new HashSet<>();

        ePaymentSet.add(EPayment.CARD);


        LocalDate localDate = LocalDate.of(2000+rd.nextInt(18),1+rd.nextInt(11),1+rd.nextInt(29));

        return CustomerOrderDto.builder()
                .customerDto(customerDto)
                .productDto(productDto)
                .date(localDate)
                .discount(BigDecimal.valueOf(1+rd.nextInt(6)))
                .quantity(1+rd.nextInt(1))
                .ePayment(ePaymentSet)
                .build();
    }
}
