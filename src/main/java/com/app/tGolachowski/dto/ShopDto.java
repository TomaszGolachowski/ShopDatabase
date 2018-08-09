package com.app.tGolachowski.dto;


import com.app.tGolachowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShopDto {
    private Long id;
    private String name;
    private CountryDto countryDto;

    public ShopDto(String name, CountryDto countryDto) {
        setName(name);
        this.countryDto = countryDto;
    }

    public void setName(String name) {
        if (name.matches("[A-Z ]*"))
            this.name = name;
        else {
            throw new MyException("SHOP: WRONG INPUT CAPITAL LETTERS OR SPACE ONLY",LocalDateTime.now());
        }
    }
}