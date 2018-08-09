package com.app.tGolachowski.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Generated
@Data
@Entity
@Builder
@Table(name = "errors")
public class Error {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    private String message;
}
