package com.app.tGolachowski.dto;


import com.app.tGolachowski.entity.Trade;
import com.app.tGolachowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProducerDto {

    private Long id;
    private String name;
    private CountryDto countryDto;
    private TradeDto tradeDto;

    public ProducerDto(String name) {
        setName(name);
    }

    public void setName(String name) {
        if (name.matches("[A-Z ]*")) this.name = name;
        else throw new MyException("PRODUCER: WRONG INPUT CAPITAL LETTERS OR SPACE ONLY",LocalDateTime.now());
    }
}
