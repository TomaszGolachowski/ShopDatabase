package com.app.tGolachowski.dto;

import com.app.tGolachowski.exceptions.MyException;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDto {

    private Long id;
    private Integer age;
    private String name;
    private String surname;
    private CountryDto CountryDto;


    public CustomerDto(int age, String name, String surname, CountryDto countryDto) {
        setName(name);
        setSurname(surname);
        setAge(age);
        CountryDto = countryDto;
    }

    public void setName(String name) {
        if (name.matches("[A-Z ]+"))
            this.name = name;
        else {
            throw new MyException("CUSTOMER: WRONG INPUT CAPITAL LETTERS OR SPACE ONLY", LocalDateTime.now());
        }
    }

    public void setAge(Integer age) {
        if (age >= 18 || !age.toString().matches("[\\d]+")) {
            this.age = age;
        } else {
            throw new MyException("CUSTOMER: WRONG INPUT AGE HAS TO BE HIGER THAN 18 AND HAS TO BE DIGIT", LocalDateTime.now());
        }
    }

    public void setSurname(String surname) {
        if (surname.matches("[A-Z ]*"))
            this.surname = surname;
        else {
            throw new MyException("CUSTOMER: WRONG INPUT CAPITAL LETTERS OR SPACE ONLY", LocalDateTime.now());
        }
    }
}
