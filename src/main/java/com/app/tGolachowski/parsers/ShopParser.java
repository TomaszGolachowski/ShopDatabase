package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.CountryDto;
import com.app.tGolachowski.dto.ShopDto;
import com.app.tGolachowski.dto.TradeDto;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.service.MyService;
import com.app.tGolachowski.service.MyServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ShopParser implements Parser<ShopDto> {

    private MyService myService = new MyServiceImpl();

    @Override
    public ShopDto parse(String line) {
            if (!Parser.isLineCorrect(line, RegularExpressions.SHOP_REGEX)) {
                throw new MyException("SHOP PARSER: REGEX ERROR", LocalDateTime.now());
            }

        List<String> shopData = Arrays.asList(line.split(";"));
        CountryDto countryDto = myService.findCountryByName(shopData.get(1));
        return ShopDto.builder().name(shopData.get(0)).countryDto(countryDto).build();
    }
}
