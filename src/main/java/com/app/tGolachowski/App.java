package com.app.tGolachowski;

import com.app.tGolachowski.dto.ErrorDto;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.repository.generic.DbConnection;
import com.app.tGolachowski.service.*;

import java.io.IOException;
import java.time.LocalDate;


public class App {
    public static void main(String[] args) throws IOException {

        MenuImpl menu = new MenuImpl();
        MyService myService = new MyServiceImpl();
        DataInitializerService dataInitializerService = new DataInitializerServiceImpl();

        dataInitializerService.initialize();
        while (menu.getProcess()) {
            try {
                menu.mainMenu();
            } catch (NumberFormatException e) {
                e.getMessage();
            } catch (MyException e) {
                System.out.println("EXC: " + e.getMessage());
                ErrorDto errorDto = ErrorDto.builder().message(e.getMessage()).date(LocalDate.now()).build();
                myService.addError(errorDto);
            } finally {
                DbConnection.getInstance().close();
            }
        }
    }
}
