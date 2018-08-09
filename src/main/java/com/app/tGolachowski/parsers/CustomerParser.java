package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.CustomerDto;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.service.MyService;
import com.app.tGolachowski.service.MyServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CustomerParser implements Parser<CustomerDto> {

    private MyService myService = new MyServiceImpl();

    @Override
    public CustomerDto parse(String line) {

        if (!Parser.isLineCorrect(line, RegularExpressions.CUSTOMER_REGEX)) {
            throw new MyException("CUSTOMER PARSER: REGEX ERROR",LocalDateTime.now());
        }

        List<String> constructorData = Arrays.asList(line.split(";"));
        return CustomerDto.builder()
                .name(constructorData.get(0))
                .surname(constructorData.get(1))
                .age(Integer.parseInt(constructorData.get(2)))
                .CountryDto(myService.findCountryByName(constructorData.get(3)))
                .build();
    }
}
