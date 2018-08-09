package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.CountryDto;
import com.app.tGolachowski.dto.ProducerDto;
import com.app.tGolachowski.dto.TradeDto;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.service.MyService;
import com.app.tGolachowski.service.MyServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ProducerParser implements Parser<ProducerDto> {

    private MyService myService = new MyServiceImpl();

    @Override
    public ProducerDto parse(String line) {
        if(!Parser.isLineCorrect(line,RegularExpressions.PRODUCER_REGEX)){
            throw new MyException("PRODUCER PARSER: REGEX ERROR", LocalDateTime.now());
        }
        List<String> producersData = Arrays.asList(line.split(";"));
        CountryDto countryDto = myService.findCountryByName(producersData.get(1));
        TradeDto tradeDto = myService.findTradeByName(producersData.get(2));

        return ProducerDto.builder().name(producersData.get(0)).countryDto(countryDto).tradeDto(tradeDto).build();
    }
}
