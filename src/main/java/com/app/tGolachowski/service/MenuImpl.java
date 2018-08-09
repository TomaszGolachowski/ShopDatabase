package com.app.tGolachowski.service;

import com.app.tGolachowski.dto.*;
import com.app.tGolachowski.entity.EPayment;
import com.app.tGolachowski.exceptions.MyException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;

public class MenuImpl implements Menu {
    public static Boolean process = true;
    private Scanner sc = new Scanner(System.in);
    private MyService myService = new MyServiceImpl();

    public MenuImpl() {
        setProcess(true);
    }

    public Boolean getProcess() {
        return process;
    }

    private void setProcess(Boolean process) {
        MenuImpl.process = process;
    }

    @Override
    public void mainMenu() throws MyException {

        System.out.println("=================================================================================================================");
        System.out.println("=================================================================================================================");

        System.out.println("\n" +
                "         dP                                  dP            dP            dP                                  \n" +
                "         88                                  88            88            88                                  \n" +
                ".d8888b. 88d888b. .d8888b. 88d888b.    .d888b88 .d8888b. d8888P .d8888b. 88d888b. .d8888b. .d8888b. .d8888b. \n" +
                "Y8ooooo. 88'  `88 88'  `88 88'  `88    88'  `88 88'  `88   88   88'  `88 88'  `88 88'  `88 Y8ooooo. 88ooood8 \n" +
                "      88 88    88 88.  .88 88.  .88    88.  .88 88.  .88   88   88.  .88 88.  .88 88.  .88       88 88.  ... \n" +
                "`88888P' dP    dP `88888P' 88Y888P'    `88888P8 `88888P8   dP   `88888P8 88Y8888' `88888P8 `88888P' `88888P' \n" +
                "                           88                                                                                \n" +
                "                           dP                                                                                \n" +
                " ");
        System.out.println("=================================================================================================================");
        System.out.println("=================================================================================================================");
        System.out.println("\n\nWELCOME TO DATABASE SYSTEM PER01 DEVELOPED BY TOMASZ GOLACHOWSKI ALL RIGHTS RESERVED");
        System.out.println("PLEASE CHOOSE REQUIRED OPTION AND CONFIRM IT BY PRESSING ENTER\n\n");


        System.out.println("1.FIND");
        System.out.println("2.ADD");
        System.out.println("3.ADDITIONAL FUNCTIONS");
        System.out.println("4.PLACE AN ORDER");
        System.out.println("5.FIND AN ORDER");
        System.out.println("9.EXIT");

        Integer choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                internalFindSwitch();
                break;

            case 2:
                internalAddSwitch();
                break;

            case 3:
                additionalServices();
                break;

            case 4:
                addCustomerOrder();
                break;

            case 5:
                showCustomerOrder();
                break;

            case 9:
                setProcess(false);
                break;
        }

    }


    @Override
    public void addCustomer() throws MyException {
        CustomerDto customerDto = new CustomerDto();
        System.out.println("===========================ADD CUSTOMER===============================");
        System.out.println("\n");
        System.out.println("PLEASE SUBMIT NAME:\n");
        customerDto.setName(sc.nextLine());

        System.out.println("PLEASE SUBMIT SURNAME:\n");
        customerDto.setSurname(sc.nextLine());

        System.out.println("PLEASE SUBMIT AGE:\n");
        customerDto.setAge(Integer.parseInt(sc.nextLine()));

        System.out.println("PLEASE SUBMIT COUNTRY:\n");
        String countryName = sc.nextLine();
        customerDto.setCountryDto(myService.findCountryByName(countryName));
        if (customerDto.getCountryDto() != null) {
            myService.addCustomer(customerDto);
        } else {
            throw new RuntimeException("CUSTOMER GENERATION ERROR");
        }
    }

    @Override
    public void addShop() throws MyException {
        ShopDto shopDto = new ShopDto();
        System.out.println("===========================ADD SHOP===============================");
        System.out.println("\n");

        System.out.println("PLEASE SUBMIT SHOPS NAME");
        shopDto.setName(sc.nextLine());

        System.out.println("PLEASE SUBMIT COUNTRY:\n");
        String countryName = sc.nextLine();
        shopDto.setCountryDto(myService.findCountryByName(countryName));
        if (shopDto.getCountryDto() == null) {
            throw new RuntimeException("SHOP GENERATION ERROR");
        }
        myService.addShop(shopDto);

    }

    @Override
    public void addProducer() throws MyException {
        ProducerDto producerDto = new ProducerDto();
        System.out.println("=========================ADD PRODUCER============================");
        System.out.println("\n");

        System.out.println("PLEASE SUBMIT PRODUCER NAME");
        producerDto.setName(sc.nextLine());

        System.out.println("PLEASE SUBMIT COUNTRY:\n");
        String countryName = sc.nextLine();
        producerDto.setCountryDto(myService.findCountryByName(countryName));
        if (producerDto.getCountryDto() == null) {
            throw new RuntimeException("PRODUCER GENERATION, WRONG GENERATED COUNTRY");
        }

        System.out.println("PLEASE SUBMIT PRODUCERS TRADE");
        String producersTrade = sc.nextLine();
        producerDto.setTradeDto(myService.findTradeByName(producersTrade));
        if (producerDto.getTradeDto() == null) {
            throw new RuntimeException("PRODUCER GENERATION ERROR");
        }

        myService.addProducer(producerDto);

    }

    @Override
    public void addProduct() throws MyException {

        ProductDto productDto = new ProductDto();
        System.out.println("=========================ADD PRODDUCT============================");
        System.out.println("\n");

        System.out.println("PLEASE SUBMIT PRODUCTS NAME");
        productDto.setName(sc.nextLine());

        System.out.println("PLEASE SUBMIT PRICE");
        productDto.setPrice(new BigDecimal(sc.nextLine()));

        System.out.println("PLEASE SUBMIT PRODUCTS CATEGORY:\n");
        String categoryName = sc.nextLine();
        System.out.println(myService.findCategoryByName(categoryName));
        CategoryDto categoryDto = (myService.findCategoryByName(categoryName));
        productDto.setCategoryDto(categoryDto);
        if (productDto.getCategoryDto() == null) {
            throw new RuntimeException("PRODUCT: WRONG GENERATED CATEGORY");
        }
        productDto.setGuaranteeComponentSet(myService.generateGuaranteeComponents());
        System.out.println("PLEASE SUBMIT PRODUCERS NAME:\n");
        String producerName = sc.nextLine();
        productDto.setProducerDto(myService.findProducerByName(producerName));
        if (productDto.getProducerDto() == null) {
            throw new RuntimeException("PRODUCER: NO PRODUCER FOUND ADD PRODUCER VIA MAIN MENU");
        }
        myService.addProduct(productDto);
    }

    @Override
    public void addStock() throws MyException {
        StockDto stockDto = new StockDto();
        System.out.println("=========================ADD STOCK============================");
        System.out.println("\n");

        System.out.println("PLEASE SUBMIT PRODUCTS NAME:\n");
        String productName = sc.nextLine();
        stockDto.setProductDto(myService.findProductByName(productName));
        if (stockDto.getProductDto() == null) {
            throw new RuntimeException("STOCK: NO PRODUCER FOUND ADD PRODUCER VIA MAIN MENU");
        }

        System.out.println("PLEASE SUBMIT SHOP NAME:\n");
        String shopName = sc.nextLine();
        ShopDto shopDto = myService.findShopByName(shopName);
        stockDto.setShopDto(shopDto);
        if (stockDto.getShopDto() == null) {
            throw new RuntimeException("STOCK: NO SHOP FOUND ADD SHOP VIA MAIN MENU");
        }

        System.out.println("PLEASE SUBMIT QUANITITY OF PRODUCT");
        stockDto.setQuantity(Integer.parseInt(sc.nextLine()));

        myService.addStock(stockDto);
    }

    @Override
    public void addCustomerOrder() throws MyException {

        System.out.println("==============================STARTING ORDER=========================");
        CustomerOrderDto customerOrderDto = new CustomerOrderDto();
        CustomerDto customerDto = myService.findCustomer();

        System.out.println("ACCOUNT HAS BEEN FOUND, WELCOME " + customerDto.getName() + " " + customerDto.getSurname());
        customerOrderDto.setCustomerDto(customerDto);

        System.out.println("===========================SET PRODUCT===============================");
        System.out.println("PLEASE PROVIDE PRODUCTS NAME");
        ProductDto productDto = myService.findProductByName(sc.nextLine());
        customerOrderDto.setProductDto(productDto);
        System.out.println("PLEASE PROVIDE QUANTITY OF PRODUCT");
        Integer quantity = Integer.parseInt(sc.nextLine());
        customerOrderDto.setQuantity(quantity);
        myService.checkIfStocksHaveEnoughProducts(customerOrderDto);

        System.out.println("PAYMENT TYPE:\n1. CASH\n2. CARD\n3. MONEY TRANSFER");
        HashSet<EPayment> ePayments = new HashSet<>();
        String paymentChoice = sc.nextLine();
        switch (paymentChoice) {
            case "1":
                ePayments.add(EPayment.CASH);
                break;
            case "2":
                ePayments.add(EPayment.CARD);
                break;
            case "3":
                ePayments.add(EPayment.MONEY_TRANSFER);
                break;
            default:
                throw new RuntimeException("WRONG PAYMENT METHOD CHOSEN");
        }
        customerOrderDto.setePayment(ePayments);

        System.out.println("PLEASE PROVIDE DISCOUNT OF ORDER IN PROCENTS (DO NOT ADD PROCENT SYMBOL)");
        customerOrderDto.setDiscount(new BigDecimal(sc.nextLine()));

        System.out.println("ORDER CONFIRMATION: ");
        System.out.println("CLIENT DETAILS: " + customerOrderDto.getCustomerDto().getName() + "\nSURNAME: " + customerOrderDto.getCustomerDto().getSurname());
        System.out.println("CLIENT ADRESS: " + customerOrderDto.getCustomerDto().getCountryDto().getName());
        System.out.println("PRODUCT DETAILS: " + customerOrderDto.getProductDto().getName());
        System.out.println("PRICE IN TOTAL: " + myService.calculateFinalPrice(customerOrderDto));

        System.out.println("\nDO YOU ACCEPT THIS ORDER? Y/N");
        if (sc.nextLine().matches("[yY]")) {

            customerOrderDto.setDate(LocalDate.now());
            try {
                myService.addCustomerOrder(customerOrderDto);
                myService.updateStockAfterOrder(customerOrderDto);
            } catch (Exception e) {
                System.out.println("ERROR: ORDER CANCELLED");
                throw e;
            }
            System.out.println("ORDER CONFIRMED");
        } else {
            System.out.println("ORDER CANCELLED");
        }

    }

    @Override
    public void internalAddSwitch() throws MyException {
        System.out.println("==========================ADD SUBMENU======================================");
        System.out.println("\n\n1.ADD CUSTOMER\n2.ADD SHOP\n3.ADD PRODUCER\n4.ADD PRODUCT\n5.ADD STOCK\n9.EXIT");
        switch (Integer.parseInt(sc.nextLine())) {
            case 1:
                addCustomer();
                break;

            case 2:
                addShop();
                break;

            case 3:
                addProducer();
                break;

            case 4:
                addProduct();
                break;

            case 5:
                addStock();
                break;

            case 9:
                System.out.println("BACK TO MAIN MENU");
                break;
        }
    }

    @Override
    public void internalFindSwitch() throws MyException {

        System.out.println("==========================FIND SUBMENU======================================");
        System.out.println("\n\n1.LIST ALL CUSTOMERS\n2.LIST ALL SHOPS\n3.LIST ALL PRODUCERS\n4.LIST ALL PRODUCTS\n5.LIST ALL STOCKS OF SHOPS\n9.EXIT");
        switch (Integer.parseInt(sc.nextLine())) {
            case 1:
                myService.findAllCustomers().stream()
                        .sorted(Comparator.comparing(CustomerDto::getId))
                        .forEach(customerDto -> System.out.println("\nCUSTOMER ID:" + customerDto.getId() + ", CUSTOMER NAME: " + customerDto.getName() + ", CUSTOMER SURNAME: " + customerDto.getSurname() + ", CUSTOMER AGE: " + customerDto.getAge() + ", CUSTOMER COUNTRY:" + customerDto.getCountryDto().getName() + "\n================"));

                break;
            case 2:
                myService.findAllShops().stream()
                        .sorted(Comparator.comparing(ShopDto::getId))
                        .forEach(shopDto -> System.out.println("SHOP ID: " + shopDto.getId() + ", SHOP NAME: " + shopDto.getName() + ", SHOP COUNTRY: " + shopDto.getCountryDto().getName() + "\n================"));

                break;

            case 3:
                myService.findAllProducers().stream()
                        .sorted(Comparator.comparing(ProducerDto::getId))
                        .forEach(producerDto -> System.out.println("PRODUCER ID: " + producerDto.getId() + ", PRODUCER NAME: " + producerDto.getName() + ", PRODUCER TRADE: " + producerDto.getTradeDto().getName() + ", PRODUCER COUNTRY: " + producerDto.getCountryDto().getName() + "\n================"));

                break;

            case 4:
                myService.findAllProducts().stream()
                        .sorted(Comparator.comparing(ProductDto::getId))
                        .forEach(productDto -> System.out.println("PRODUCT ID: " + productDto.getId() + ", PRODUCT NAME: " + productDto.getName() + ", PRODUCT PRICE: " + productDto.getPrice() + ", PRODUCT CATEGORY: " + productDto.getCategoryDto().getName() + ", PRODUCT PRODUCER: " + productDto.getProducerDto().getName() + ", PRODUCT WARRANITY: " + productDto.getGuaranteeComponentSet() + "\n================"));

                break;

            case 5:
                myService.findAllStocks().stream()
                        .sorted(Comparator.comparing(stockDto -> stockDto.getShopDto().getName()))
                        .forEach(stockDto -> System.out.println("STOCK ID: " + stockDto.getId() + ", STOCK SHOP: " + stockDto.getShopDto().getName() + ", STOCK PRODUCT: " + stockDto.getProductDto().getName() + ", PRODUCT CATEGORY: " + stockDto.getProductDto().getCategoryDto().getName() + ", STOCK QUANTITY: " + stockDto.getQuantity() + "\n================"));

                break;

            case 9:
                System.out.println("\nBACK TO MAIN MENU");
                break;
        }
    }

    @Override
    public void showCustomerOrder() throws MyException {
        myService.showCustomerOrder();

    }

    @Override
    public void additionalServices() throws MyException {
        System.out.println("========================= ADDITIONAL SERVICES======================================");
        System.out.println("\n\n1.FIND MOST EXPENSIVE PRODUCTS IN EACH CATEGORY\n2.DEMOGRAPHIC LIST OF PRODUCTS BY COUNTRY AND AGE\n3.FIND PRODUCTS BY WARRANTY" +
                "\n4.CHECK FOR FOREIGN NATIONALITY PRODUCTS IN EACH SHOP\n5.ADD STOCK\n6.FIND PRODUCTS WHICH WERE BOUGHT BETWEEN TWO DATES AND ABOVE GIVEN PRICE\n7.FIND PRODUCTS ORDERED BY\n" +
                "8.LIST ALL CUSTOMERS WHO ORDERED PRODUCT MADE IN THEIR COUNTRYn\n9.EXIT");
        switch (Integer.parseInt(sc.nextLine())) {
            case 1:
                myService.theMostExpensiveInEachCategory();

                break;

            case 2:
                myService.reportByAgeAndDemographics();

                break;

            case 3:
                myService.viableWarranty();

                break;

            case 4:
                myService.checkShopsForForeignProducts();

                break;

            case 5:
                myService.findProducersWhichProducedMoreThan();

                break;

            case 6:
                myService.findProductsFromYearToYEarAndAboveGivenPrice();

                break;

            case 7:
                myService.findProductsOrderedBy();

                break;

            case 8:
                myService.findCustomersWhichOrderedProductFromTheirCountry();

                break;

            case 9:
                System.out.println("BACK TO MAIN MENU");
                break;
        }
    }
}




