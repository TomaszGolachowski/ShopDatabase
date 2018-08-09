package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.CountryDto;
import com.app.tGolachowski.exceptions.MyException;

import java.time.LocalDateTime;

public class CountryParser implements Parser<CountryDto> {
    @Override
    public CountryDto parse(String line) {
        if(!Parser.isLineCorrect(line,RegularExpressions.COUNTRY_REGEX)) {
            throw new MyException("COUNTRY PARSER: REGEX ERROR",LocalDateTime.now());
        }
        return CountryDto.builder().name(line).build();
    }
}
