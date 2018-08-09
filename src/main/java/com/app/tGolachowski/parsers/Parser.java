package com.app.tGolachowski.parsers;

import com.app.tGolachowski.exceptions.MyException;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FunctionalInterface
public interface Parser<T> {
    T parse(String line);

    static boolean isLineCorrect(String line, String regex) {
        return line != null && line.matches(regex);
    }

    static <T> List<T> parseFile(String filename, Parser<T> parser) {
        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            return lines.map(parser::parse).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("PARSE FILE EXCEPTION", LocalDateTime.now());
        }
    }
}
