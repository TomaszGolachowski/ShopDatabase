package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.TradeDto;
import com.app.tGolachowski.exceptions.MyException;


import java.time.LocalDateTime;

public class TradeParser implements Parser<TradeDto> {
    @Override
    public TradeDto parse(String line) {
        if (!Parser.isLineCorrect(line,RegularExpressions.TRADE_REGEX)){
            throw new MyException("COUNTRY PARSER: REGEX ERROR", LocalDateTime.now());
        }
        return TradeDto.builder().name(line).build();
    }
}
