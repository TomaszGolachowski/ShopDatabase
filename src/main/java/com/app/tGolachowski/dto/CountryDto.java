package com.app.tGolachowski.dto;

import com.app.tGolachowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data

public class CountryDto {
    private Long id;
    private String name;

    public CountryDto(String name) {
        setName(name);
    }


    public void setName(String name) {
        if (name.matches("[A-Z ]*"))
            this.name = name;
        else {
            throw new MyException("COUNTRY: WRONG INPUT CAPITAL LETTERS OR SPACE ONLY", LocalDateTime.now());
        }
    }
}
