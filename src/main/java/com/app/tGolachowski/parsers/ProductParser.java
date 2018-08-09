package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.CategoryDto;
import com.app.tGolachowski.dto.ProducerDto;
import com.app.tGolachowski.dto.ProductDto;
import com.app.tGolachowski.entity.GuaranteeComponent;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.service.MyService;
import com.app.tGolachowski.service.MyServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class ProductParser implements Parser<ProductDto> {

    private MyService myService = new MyServiceImpl();

    @Override
    public ProductDto parse(String line) {
        if(!Parser.isLineCorrect(line,RegularExpressions.PRODUCT_REGEX)){
            throw new MyException("PRODUCT PARSER: REGEX ERROR", LocalDateTime.now());
        }
        List<String> productData = Arrays.asList(line.split(";"));
        ProducerDto producerDto = myService.findProducerByName(productData.get(3));
        CategoryDto categoryDto = myService.findCategoryByName(productData.get(2));

        Random rd = new Random();
        Set<GuaranteeComponent> guaranteeComponentSet = new HashSet<>();
        if(rd.nextInt(10)<5) {
            guaranteeComponentSet.add(GuaranteeComponent.HELP_DESK);
        }
        if (rd.nextInt(10)>5) {
            guaranteeComponentSet.add(GuaranteeComponent.MONEY_BACK);
        }
        if(rd.nextInt(10)>2) {
            guaranteeComponentSet.add(GuaranteeComponent.SERVICE);
        }

        return ProductDto.builder().name(productData.get(0))
                .producerDto(producerDto)
                .categoryDto(categoryDto)
                .guaranteeComponentSet(guaranteeComponentSet)
                .price(new BigDecimal(productData.get(1)))
                .build();

    }
}
