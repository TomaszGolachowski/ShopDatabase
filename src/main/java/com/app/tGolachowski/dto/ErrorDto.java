package com.app.tGolachowski.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class ErrorDto {

    private Long id;
    private LocalDate date;
    private String message;
}
