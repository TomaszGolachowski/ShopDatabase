package com.app.tGolachowski.parsers;

import com.app.tGolachowski.dto.CategoryDto;
import com.app.tGolachowski.exceptions.MyException;

import java.time.LocalDateTime;

public class CategoryParser implements Parser<CategoryDto> {
    @Override
    public CategoryDto parse(String line) {
        if (!Parser.isLineCorrect(line, RegularExpressions.CATEGORY_REGEX)) {
            throw new MyException("CATEGORY_PARSER: REGEX ERROR",LocalDateTime.now());
        }
        return CategoryDto.builder().name(line).build();
    }
}
