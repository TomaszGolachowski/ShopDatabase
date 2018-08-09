package com.app.tGolachowski.dto;

import com.app.tGolachowski.exceptions.MyException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;

    public CategoryDto(String name) {
        setName(name);
    }

    public void setName(String name) {
        if (name.matches("[A-Z ]*"))
            this.name = name;
        else {
            throw new MyException("CATEGORY: WRONG INPUT CAPITAL LETTERS OR SPACE ONLY", LocalDateTime.now());
        }
    }
}
