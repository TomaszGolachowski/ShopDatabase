package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.ProductDto;
import com.app.tGolachowski.dto.ShopDto;
import com.app.tGolachowski.dto.StockDto;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.service.MyService;
import com.app.tGolachowski.service.MyServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StocksParser implements Parser<StockDto> {

    MyService myService = new MyServiceImpl();

    @Override
    public StockDto parse(String line) {
        if(!Parser.isLineCorrect(line,RegularExpressions.STOCK_REGEX)){
            throw new MyException("STOCK PARSER: REGEX ERROR", LocalDateTime.now());
        }

        List<String> stocksData = Arrays.asList(line.split(";"));

        ProductDto productDto = myService.findProductByName(stocksData.get(0));
        ShopDto shopDto = myService.findShopByName(stocksData.get(1));
        return StockDto.builder()
                .shopDto(shopDto)
                .productDto(productDto)
                .quantity(Integer.parseInt(stocksData.get(2))).build();

    }
}
