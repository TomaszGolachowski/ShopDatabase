package com.app.tGolachowski.service;


import com.app.tGolachowski.dto.*;
import com.app.tGolachowski.entity.*;
import com.app.tGolachowski.exceptions.MyException;
import com.app.tGolachowski.repository.interfaces.*;
import com.app.tGolachowski.repository.implementations.CateogryRepositoryImpl;
import com.app.tGolachowski.repository.implementations.CountryRepositoryImpl;
import com.app.tGolachowski.repository.implementations.CustomerRepositoryImpl;
import com.app.tGolachowski.repository.implementations.CustomerOrderRepositoryImpl;
import com.app.tGolachowski.repository.implementations.ErrorRepositoryImpl;
import com.app.tGolachowski.repository.implementations.ProducerRepositoryImpl;
import com.app.tGolachowski.repository.implementations.ProductRepositoryImpl;
import com.app.tGolachowski.repository.implementations.ShopRepositoryImpl;
import com.app.tGolachowski.repository.implementations.StockRepositoryImpl;
import com.app.tGolachowski.repository.implementations.TradeRepositoryImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MyServiceImpl implements MyService {

    private MyMapper myMapper = new MyMapper();
    private CountryRepository countryRepository = new CountryRepositoryImpl();
    private CustomerRepository customerRepository = new CustomerRepositoryImpl();
    private CustomerOrdersRepository customerOrdersRepository = new CustomerOrderRepositoryImpl();
    private ProductRepository productRepository = new ProductRepositoryImpl();
    private CategoryRepository categoryRepository = new CateogryRepositoryImpl();
    private ProducerRepository producerRepository = new ProducerRepositoryImpl();
    private TradeRepository tradeRepository = new TradeRepositoryImpl();
    private StockRepository stockRepository = new StockRepositoryImpl();
    private ShopRepository shopRepository = new ShopRepositoryImpl();
    private ErrorRepository errorRepository = new ErrorRepositoryImpl();
    private Scanner sc = new Scanner(System.in);




    @Override
    public void addCountry(CountryDto countryDto) throws MyException {
        countryRepository.addOrUpdate(myMapper.fromCountryDtoToCountry(countryDto));
    }

    @Override
    public void addCountry(String name) throws MyException {
        countryRepository.addOrUpdate(Country.builder().name(name).build());
    }

    @Override
    public CountryDto findCountryById(Long id) throws MyException {
        return myMapper.fromCountryToCountryDto(countryRepository.findOneById(id)
                .orElseThrow(() -> new MyException("COUNTRY: PLEASE ADD COUNTRY IN MAIN MENU", LocalDateTime.now())));
    }

    @Override
    public CountryDto findCountryByName(String name) throws MyException {
        Country country = countryRepository.findAll()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (country == null) {
            System.out.println("NO COUNTRY WAS FOUND, DO YOU WISH TO ADD COUNTRY: " + name + " Y/N?");
            if (sc.nextLine().matches("[Y|y]")) {
                CountryDto countryDto = new CountryDto(name);
                if (countryDto.getName() != null) {
                    addCountry(countryDto);
                    System.out.println("ADDED COUNTRY: " + countryDto);
                    countryDto = findCountryByName(name);
                    return countryDto;
                } else {
                    throw new MyException("COUNTRY: ERROR IN ADDING A COUTNRY", LocalDateTime.now());
                }
            }
        }
        return myMapper.fromCountryToCountryDto(country);
    }


    @Override
    public void addCustomer(CustomerDto customerDto) throws MyException {
        checkCustomerValidation(customerDto);
        customerRepository.addOrUpdate(myMapper.fromCustomerDtoToCustomer(customerDto));
    }

    @Override
    public CustomerDto findCustomer() throws MyException {
        System.out.println("\n\n===========================SET CUSTOMER===============================");
        CustomerDto customerDto = CustomerDto.builder().build();
        System.out.println("\n");
        System.out.println("PLEASE SUBMIT NAME:\n");
        customerDto.setName(sc.nextLine());

        System.out.println("PLEASE SUBMIT SURNAME:\n");
        customerDto.setSurname(sc.nextLine());

        System.out.println("PLEASE SUBMIT COUNTRY:\n");
        String countryName = sc.nextLine();
        customerDto.setCountryDto(findCountryByName(countryName));

        customerDto = findCustomerByNameSurnameAndCountry(customerDto);
        customerDto.setCountryDto(findCountryByName(customerDto.getCountryDto().getName()));
        if (customerDto.getName() == null) {
            throw new MyException("CUSTOMER: NO CUSTOMER WAS FOUND", LocalDateTime.now());
        }
        return customerDto;
    }

    private void checkCustomerValidation(CustomerDto customerDto) throws MyException {
        if (customerRepository.findAll().stream()
                .map(customer -> myMapper.fromCustomerToCustomerDto(customer))
                .anyMatch(cDto ->
                        cDto.getName().equals(customerDto.getName())
                                && cDto.getSurname().equals(customerDto.getSurname())
                                && cDto.getCountryDto().equals(customerDto.getCountryDto())
                )) {
            throw new MyException("CUSTOMER: FOUND DUPLICATE", LocalDateTime.now());
        }
    }


    @Override
    public CustomerDto findCustomerById(Long id) throws MyException {
        Customer customer = customerRepository.findOneById(id)
                .orElseThrow(() -> new MyException("CUSTOMER: NO ACCOUNT WAS FOUND, PLEASE REGISTER IN MAIN MENU", LocalDateTime.now()));
        return myMapper.fromCustomerToCustomerDto(customer);
    }

    @Override
    public CustomerDto findCustomerByNameSurnameAndCountry(CustomerDto customerDto) throws MyException {
        Customer customer = customerRepository.findAll().stream().filter(c -> c.getName().equals(customerDto.getName())
                && c.getSurname().equals(customerDto.getSurname()) && c.getCountry().equals(myMapper.fromCountryDtoToCountry(customerDto.getCountryDto())))
                .findAny().orElseThrow(() -> new MyException("CUSTOMER: NO ACCOUNT WAS FOUND, PLEASE REGISTER IN MAIN MENU", LocalDateTime.now()));

        return myMapper.fromCustomerToCustomerDto(customer);
    }

    @Override
    public void showCustomerOrder() throws MyException {
        System.out.println("PLEASE PROVIDE ID OF THE ORDER: ");
        Long id = sc.nextLong();
        CustomerOrderDto customerOrderDto = findCustomerOrderById(id);

        System.out.println("ORDER ID: " + customerOrderDto.getId());
        System.out.println("ORDER ISSIUED: " + customerOrderDto.getDate() + "\n");

        System.out.println("PRODUCT NAME: " + customerOrderDto.getProductDto().getName());
        System.out.println("PRODUCT PRICE: " + customerOrderDto.getProductDto().getPrice());
        System.out.println("PRODUCT QUANTITY: " + customerOrderDto.getQuantity());
        System.out.println("DISCOUNT: " + customerOrderDto.getDiscount());
        System.out.println("OVERALL PRICE: " + calculateFinalPrice(customerOrderDto));

        System.out.println("WARRANTY SERVICES: " + customerOrderDto.getProductDto().getGuaranteeComponentSet());
        System.out.println("WARRANTY ENDS: " + customerOrderDto.getDate().plusYears(2));

        System.out.println("CUSTOMER NAME: " + customerOrderDto.getCustomerDto().getName());
        System.out.println("CUSTOMER SURNNAME: " + customerOrderDto.getCustomerDto().getSurname());
        System.out.println("CUSTOMER COUNTRY: " + customerOrderDto.getCustomerDto().getCountryDto().getName());
        System.out.println("CUSTOMER AGE: " + customerOrderDto.getCustomerDto().getAge());
    }

    @Override
    public Set<CustomerDto> findAllCustomers() throws MyException {
        return customerRepository.findAll().stream()
                .map(customer -> myMapper.fromCustomerToCustomerDto(customer))
                .collect(Collectors.toSet());
    }

    @Override
    public void addCustomerOrder(CustomerOrderDto customerOrderDto) throws MyException {
        customerOrdersRepository.addOrUpdate(myMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto));
    }

    @Override
    public CustomerOrderDto findCustomerOrderById(Long id) throws MyException {
        return myMapper.fromCustomerOrderToCustomerOrderDto(customerOrdersRepository.findOneById(id).orElseThrow(() -> new MyException("CUSTOMER_ORDER: INSUFFICIENT PRODUCT COUNTS IN ALL STOCKS", LocalDateTime.now())));
    }

    @Override
    public Boolean checkIfStocksHaveEnoughProducts(CustomerOrderDto customerOrderDto) throws MyException {
        Product product = myMapper.fromProductDtoToProduct(customerOrderDto.getProductDto());
        stockRepository.findAll()
                .stream()
                .filter(s -> s.getProduct().equals(product) && s.getQuantity() >= myMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto).getQuantity())
                .findAny().orElseThrow(() -> new MyException("STOCK: INSUFFICIENT PRODUCT COUNTS IN ALL STOCKS", LocalDateTime.now()));
        return true;
    }

    @Override
    public void updateStockAfterOrder(CustomerOrderDto customerOrderDto) throws MyException {
        Product product = myMapper.fromProductDtoToProduct(customerOrderDto.getProductDto());
        Stock stockS = stockRepository.findAll()
                .stream()
                .filter(s -> s.getProduct().equals(product) && s.getQuantity() >= myMapper.fromCustomerOrderDtoToCustomerOrder(customerOrderDto).getQuantity())
                .findAny().orElseThrow(() -> new MyException("STOCK: INSUFFICIENT COUNTS IN ALL STOCKS",LocalDateTime.now()));

        stockS.setQuantity(stockS.getQuantity() - customerOrderDto.getQuantity());
        stockRepository.addOrUpdate(stockS);
    }


    @Override
    public void addProduct(ProductDto productDto) throws MyException {
        checkProductValidation(productDto);
        productRepository.addOrUpdate(myMapper.fromProductDtoToProduct(productDto));
    }

    private void checkProductValidation(ProductDto productDto) throws MyException {
        if (productRepository.findAll().stream()
                .map(product -> myMapper.fromProductToProductDto(product))
                .anyMatch(pDto ->
                        pDto.getName().equals(productDto.getName())
                                && pDto.getCategoryDto().equals(productDto.getCategoryDto())
                                && pDto.getProducerDto().equals(productDto.getProducerDto())
                )) {
            throw new MyException("PRODUCT: DUPLICATE FOUND",LocalDateTime.now());
        }
    }

    @Override
    public ProductDto findProductById(Long id) throws MyException {
        return myMapper.fromProductToProductDto(productRepository.findOneById(id).orElseThrow(() -> new MyException("PRODUCT: NO PRODUCT FOUND",LocalDateTime.now())));
    }

    @Override
    public ProductDto findProductByNameAndCategory(String name, String categoryName) throws MyException {
        return myMapper.fromProductToProductDto(productRepository.findAll()
                .stream()
                .filter(p -> p.getName().equals(name) && p.getCategory().getName().equals(categoryName))
                .findFirst().orElseThrow(() -> new MyException("PRODUCT: NO PRODUCT FOUND", LocalDateTime.now())));
    }

    @Override
    public ProductDto findProductByName(String name) throws MyException {
        List<Product> products = productRepository.findAll()
                .stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toList());

        Map<Long, String> productMap = new HashMap<>();

        for (Product product : products) {
            productMap.put(product.getId(), "CATEGORY: " + product.getCategory().getName() + " PRODUCER: " + product.getProducer().getName());
        }

        if (products.size() > 1) {
            System.out.println("FOUND " + productMap.size() + " PRODUCTS OF GIVEN NAME, PLEASE SELECT DESIRED ONE:\n" + productMap);
            Long productId = Long.parseLong(sc.nextLine());
            return findProductById(productId);
        }
        return findProductById(productMap.keySet().stream()
                .findFirst()
                .orElseThrow(() -> new MyException("PRODUCT: NO PRODUCT FOUND", LocalDateTime.now())));
    }

    @Override
    public Set<ProductDto> findAllProducts() throws MyException {
        return productRepository.findAll().stream()
                .map(product -> myMapper.fromProductToProductDto(product)).collect(Collectors.toSet());
    }


    @Override
    public void addCategory(CategoryDto categoryDto) throws MyException {
        Category category = myMapper.fromCategoryDtoToCategory(categoryDto);
        categoryRepository.addOrUpdate(category);
    }

    @Override
    public CategoryDto findCategoryByName(String name) throws MyException {
        Category category = categoryRepository.findAll()
                .stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);

        if (category == null) {
            System.out.println("NO CATEGORY WAS FOUND, DO YOU WISH TO ADD CATEGORY: " + name + " Y/N?");
            if (sc.nextLine().matches("[Y|y]")) {
                CategoryDto categoryDto = new CategoryDto(name);
                if (categoryDto.getName() != null) {
                    addCategory(categoryDto);
                    System.out.println("ADDED CATEGORY: " + categoryDto);
                    categoryDto = findCategoryByName(name);
                    return categoryDto;
                } else {
                    throw new MyException("CATEGORY: ERROR ADDING NEW ENTITY", LocalDateTime.now());
                }
            }
        }
        return myMapper.fromCategoryToCategoryDto(category);
    }

    @Override
    public CategoryDto findCategoryById(Long id) throws MyException {
        return myMapper.fromCategoryToCategoryDto(categoryRepository.findOneById(id).orElseThrow(() -> new MyException("CATEGORY: NO CATEGORY FOUND", LocalDateTime.now())));
    }

    @Override
    public void addProducer(ProducerDto producerDto) throws MyException {
        checkProducerValidation(producerDto);
        Producer producer = myMapper.fromProducerDtoToProducer(producerDto);
        producerRepository.addOrUpdate(producer);
    }


    private void checkProducerValidation(ProducerDto producerDto) throws MyException {
        if (producerRepository.findAll().stream()
                .map(producer -> myMapper.fromProducerToProducerDto(producer))
                .anyMatch(pDto ->
                        pDto.getName().equals(producerDto.getName())
                                && pDto.getTradeDto().equals(producerDto.getTradeDto())
                                && pDto.getCountryDto().equals(producerDto.getCountryDto()
                        ))) {
            throw new MyException("PRODUCER: PRODUCER DUPLICATE", LocalDateTime.now());
        }
    }

    @Override
    public Set<ProducerDto> findAllProducers() throws MyException {
        try {
            return producerRepository.findAll().stream()
                    .map(producer -> myMapper.fromProducerToProducerDto(producer))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new MyException("PRODUCER: ERROR FINDING PRODUCERS", LocalDateTime.now());
        }
    }

    @Override
    public ProducerDto findProducerById(Long id) throws MyException {
        return myMapper.fromProducerToProducerDto(producerRepository.findOneById(id).orElseThrow(() -> new MyException("PRODUCER: NO PRODUCER FOUND", LocalDateTime.now())));
    }

    @Override
    public ProducerDto findProducerByName(String name) throws MyException {
        return myMapper.fromProducerToProducerDto(producerRepository
                .findAll()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new MyException("PRODUCER: NO PRODUCER FOUND", LocalDateTime.now())));
    }

    @Override
    public void addTrade(TradeDto tradeDto) throws MyException {
        try {
            tradeRepository.addOrUpdate(myMapper.fromTradeDtoToTrade(tradeDto));
        } catch (Exception e) {
            throw new MyException("TRADE: ERROR ADDING ENTITY", LocalDateTime.now());
        }
    }

    @Override
    public void addTrade(String name) throws MyException {
        try {
            tradeRepository.addOrUpdate(Trade.builder().name(name).build());
        } catch (Exception e) {
            throw new MyException("TRADE: ERROR ADDING ENTITY", LocalDateTime.now());
        }
    }

    @Override
    public TradeDto findTradeDtoById(Long id) throws MyException {
        return myMapper.fromTradeToTradeDto(tradeRepository.findOneById(id).orElseThrow(() -> new MyException("TRADE: NO TRADE FOUND",LocalDateTime.now())));
    }

    @Override
    public TradeDto findTradeByName(String name) throws MyException {
        Trade trade = (tradeRepository.findAll().stream()
                .filter(t -> t.getName().equals(name))
                .findFirst().orElse(null));

        if (trade == null) {
            System.out.println("NO TRADE WAS FOUND, DO YOU WISH TO ADD TRADE: " + name + " Y/N?");
            if (sc.nextLine().matches("[Y|y]")) {
                TradeDto tradeDto = new TradeDto(name);
                if (tradeDto.getName() != null) {
                    addTrade(tradeDto);
                    System.out.println("ADDED TRADE: " + tradeDto);
                    tradeDto = findTradeByName(name);
                    return tradeDto;
                } else {
                    return null;
                }
            }
        }
        return myMapper.fromTradeToTradeDto(trade);
    }

    @Override
    public void addStock(StockDto stockDto) throws MyException {
        StockDto checkedStock = myMapper.fromStockToStockDto(checkStockValidation(stockDto));
        if (checkedStock == null) {
            Stock stock = myMapper.fromStockDtoToStock(stockDto);
            stockRepository.addOrUpdate(stock);
        } else if (checkedStock.getShopDto() != null) {
            System.out.println("DETECTED STOCK REPETITION, DO YOU WISH TO UPDATE IT? Y/N");
            if (sc.nextLine().matches("[Yy]")) {
                checkedStock.setQuantity(stockDto.getQuantity() + checkedStock.getQuantity());
                stockRepository.addOrUpdate(myMapper.fromStockDtoToStock(checkedStock));
            }
        } else throw new MyException("STOCK: DUPLICATE FOUND", LocalDateTime.now());
    }

    @Override
    public Set<StockDto> findAllStocks() throws MyException {
        try {
            return stockRepository.findAll().stream()
                    .map(stock -> myMapper.fromStockToStockDto(stock))
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            throw new MyException("STOCK: NO STOCK FOUND", LocalDateTime.now());
        }
    }

    @Override
    public StockDto findStockById(Long id) throws MyException {
        return myMapper.fromStockToStockDto(stockRepository.findOneById(id).orElseThrow(() -> new MyException("STOCK: NO STOCK FOUND", LocalDateTime.now())));
    }

    @Override
    public Stock checkStockValidation(StockDto stockDto) throws MyException {
        Stock stockTest = myMapper.fromStockDtoToStock(stockDto);
        return stockRepository.findAll().stream().filter(stock ->
                stock.getProduct().equals(stockTest.getProduct()) &&
                        stock.getShop().equals(stockTest.getShop())).findAny().orElse(null);
    }

    @Override
    public void addShop(ShopDto shopDto) throws MyException {
        try {
            checkShopValidation(shopDto);
            shopRepository.addOrUpdate(myMapper.fromShopDtoToShop(shopDto));
        } catch (Exception e) {
            throw new MyException("SHOP: ERROR ADDING ENTITY", LocalDateTime.now());
        }
    }

    @Override
    public ShopDto findShopByName(String name) throws MyException {

        List<Shop> shops = shopRepository.findAll()
                .stream()
                .filter(s -> s.getName().equals(name))
                .collect(Collectors.toList());

        Map<Long, String> shopMap = new HashMap<>();

        for (Shop shop : shops) {
            shopMap.put(shop.getId(), shop.getCountry().getName());
        }

        if (shops.size() > 1) {
            System.out.println("FOUND " + shops.size() + " SHOP OF GIVEN NAME, PLEASE SELECT DESIRED ONE:\n" + shopMap);
            Long shopId = Long.parseLong(sc.nextLine());
            return findShopById(shopId);
        }
        return findShopById(shopMap.keySet().stream()
                .findFirst()
                .orElseThrow(() -> new MyException("SHOP: NO SHOP WAS FOUND", LocalDateTime.now())));
    }

    @Override
    public ShopDto findShopById(Long id) throws MyException {
        return myMapper.fromShopToShopDto(shopRepository.findOneById(id).orElseThrow(() -> new MyException("SHOP: NO SHOP WAS FOUND", LocalDateTime.now())));
    }

    @Override
    public List<ShopDto> findAllShops() throws MyException {
        try {
            return shopRepository.findAll().stream()
                    .map(shop -> myMapper.fromShopToShopDto(shop))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException("SHOP: NO SHOP FOUND", LocalDateTime.now());
        }
    }

    private void checkShopValidation(ShopDto shopDto) throws MyException {
        if (shopRepository.findAll().stream()
                .map(shop -> myMapper.fromShopToShopDto(shop))
                .anyMatch(sDto ->
                        sDto.getName().equals(shopDto.getName())
                                && sDto.getCountryDto().equals(shopDto.getCountryDto()
                        ))) {
            throw new MyException("SHOP: DUPLICATE FOUND", LocalDateTime.now());
        }
    }

    @Override
    public void addError(ErrorDto errorDto) throws MyException {
        try {
            errorRepository.addOrUpdate(myMapper.fromErrorDtoToError(errorDto));
        } catch (Exception e) {
            throw new MyException("ERROR: ERROR ADDING ENTITY", LocalDateTime.now());
        }
    }

    @Override
    public void theMostExpensiveInEachCategory() throws MyException {

        Map<String, String> map = new HashMap<>();
        productRepository.findAll().stream()
                .map(product -> myMapper.fromProductToProductDto(product))
                .sorted(Comparator.comparing(ProductDto::getPrice
                )).forEach(p -> map.put(p.getCategoryDto().getName() + " ", " PRODUCT NAME: " + p.getName() +
                ", PRODUCT PRODUCER: " + p.getProducerDto().getName() + ", PRODUCT PRICE: " + p.getPrice() +
                ", PRODUCT WAS ORDERED " + countHowManyOrders(p) + " TIMES"));

        map.entrySet().forEach(System.out::println);
    }

    @Override
    public String countHowManyOrders(ProductDto productDto) throws MyException {

        Long result = customerOrdersRepository.findAll().stream()
                .map(customerOrder -> myMapper.fromProductToProductDto(customerOrder.getProduct()))
                .filter(productDto1 -> productDto1.equals(productDto)).count();

        return result.toString();
    }

    @Override
    public List<ProductDto> findProductsByAgeAndDemographics() throws MyException {
        System.out.println("PLEASE PROVIDE COUNTRY");
        CountryDto countryDto = findCountryByName(sc.nextLine());
        System.out.println("PLEASE PROVIDE BOTTOM LEVEL OF CUSTOMERS AGE");
        int minAge = Integer.parseInt(sc.nextLine());
        System.out.println("PLEASE PROVIDE UPPER LEVEL OF CUSTOMERS AGE");
        int maxAge = Integer.parseInt(sc.nextLine());

        return customerOrdersRepository.findAll().stream()
                .map(customerOrder -> myMapper.fromCustomerOrderToCustomerOrderDto(customerOrder))
                .filter(customerOrderDto -> customerOrderDto.getCustomerDto().getCountryDto().equals(countryDto)
                        && customerOrderDto.getCustomerDto().getAge() < maxAge
                        && customerOrderDto.getCustomerDto().getAge() > minAge)
                .map(CustomerOrderDto::getProductDto)
                .sorted(Comparator.comparing(ProductDto::getPrice))
                .collect(Collectors.toList());
    }

    @Override
    public void reportByAgeAndDemographics() throws MyException {
        List<ProductDto> productDtos = findProductsByAgeAndDemographics();
        productDtos.forEach(productDto -> System.out.println("PRODUCT NAME: " + productDto.getName()
                + ", PRODUCT PRICE: " + productDto.getPrice() + ", PRODUCT CATEGORY: " + productDto.getCategoryDto().getName() +
                ", PRODUCT PRODUCER: " + productDto.getProducerDto().getName() + ", PRODUCER COUNTRY: " + productDto.getProducerDto().getCountryDto().getName()));
    }

    @Override
    public void viableWarranty() throws MyException {

        Set<GuaranteeComponent> guaranteeComponents = generateGuaranteeComponents();

        productRepository.findAll().stream()
                .map(product -> myMapper.fromProductToProductDto(product))
                .filter(productDto -> productDto.getGuaranteeComponentSet().containsAll(guaranteeComponents))
                .sorted((o1, o2) -> Integer.compare(o1.getCategoryDto().getName().compareTo(o2.getCategoryDto().getName()), 0))
                .map(productDto -> "CATEGORY: " + productDto.getCategoryDto().getName() + ", PRODUCT: " + productDto.getName())
                .forEach(System.out::println);
    }

    @Override
    public Set<GuaranteeComponent> generateGuaranteeComponents() throws MyException {
        Set<GuaranteeComponent> guaranteeComponentSet = new HashSet<>();
        String guaranteeChoice;
        Boolean selector = true;
        while (selector) {
            System.out.println("SELECT GUARANTEE OPTIONS: 1.HELP_DESK, 2.MONEY_BACK, 3.SERVICE, 4.EXCHANGE, 5.DONE");
            guaranteeChoice = sc.nextLine();
            switch (guaranteeChoice) {
                case "1":
                    if (!guaranteeComponentSet.contains(GuaranteeComponent.HELP_DESK))
                        guaranteeComponentSet.add(GuaranteeComponent.HELP_DESK);
                    else
                        System.out.println("REPETITION -> HELP DESK");
                    break;
                case "2":
                    if (!guaranteeComponentSet.contains(GuaranteeComponent.MONEY_BACK))
                        guaranteeComponentSet.add(GuaranteeComponent.MONEY_BACK);
                    else
                        System.out.println("REPETITION -> MONEY BACK");
                    break;
                case "3":
                    if (!guaranteeComponentSet.contains(GuaranteeComponent.SERVICE))
                        guaranteeComponentSet.add(GuaranteeComponent.SERVICE);
                    else
                        System.out.println("REPETITION -> SERVICE");
                    break;
                case "4":
                    if (!guaranteeComponentSet.contains(GuaranteeComponent.EXCHANGE))
                        guaranteeComponentSet.add(GuaranteeComponent.EXCHANGE);
                    else
                        System.out.println("REPETITION -> EXCHANGE");
                    break;
                case "5":
                    selector = false;
                    break;
            }
        }
        return guaranteeComponentSet;
    }

    @Override
    public void checkShopsForForeignProducts() throws MyException {
        stockRepository.findAll().stream()
                .map(stock -> myMapper.fromStockToStockDto(stock))
                .filter(stockDto -> !checkForDepartments(stockDto.getShopDto()).contains(stockDto.getProductDto().getProducerDto().getCountryDto()))
                .forEach(stockDto -> System.out.println("PRODUCT: " + stockDto.getProductDto().getName()
                        + ", PRODUCER CONTRY: " + stockDto.getProductDto().getProducerDto().getCountryDto().getName() +
                        ", SHOP NAME: " + stockDto.getShopDto().getName() + ", SHOP COUNTRY: " + stockDto.getShopDto().getCountryDto().getName()));

    }

    @Override
    public Set<CountryDto> checkForDepartments(ShopDto shopDto) throws MyException {
        return shopRepository.findAll().stream()
                .map(shop -> myMapper.fromShopToShopDto(shop))
                .filter(sDto -> sDto.equals(shopDto))
                .map(ShopDto::getCountryDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void findProducersWhichProducedMoreThan() throws MyException {
        System.out.println("PLEASE PROVIDE SUM OFF ALL PRODUCTS CREATED BY PRODUCER: ");
        Integer limit = Integer.parseInt(sc.nextLine());

        Map<String, Long> map = new HashMap<>();

        producerRepository.findAll().stream()
                .map(producer -> myMapper.fromProducerToProducerDto(producer))
                .forEach(p ->
                        map.put(p.getName(), stockRepository.findAll().stream()
                                .map(stock -> myMapper.fromStockToStockDto(stock))
                                .filter(stockDto -> stockDto.getProductDto().getProducerDto().getName().equals(p.getName()))
                                .collect(Collectors.summarizingInt(StockDto::getQuantity)).getSum() + customerOrdersRepository.findAll().stream()
                                .map(customerOrder -> myMapper.fromCustomerOrderToCustomerOrderDto(customerOrder))
                                .filter(customerOrderDto -> customerOrderDto.getProductDto().getProducerDto().getName().equals(p.getName()))
                                .collect(Collectors.summarizingInt(CustomerOrderDto::getQuantity)).getSum()));

        map.entrySet().stream().filter(p -> p.getValue() > limit).forEach(System.out::println);
    }

    @Override
    public BigDecimal calculateFinalPrice(CustomerOrderDto customerOrderDto) throws MyException {
        return customerOrderDto.getProductDto().getPrice().multiply((BigDecimal.valueOf(100).subtract(customerOrderDto.getDiscount()))).divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(customerOrderDto.getQuantity()));
    }

    @Override
    public void findProductsFromYearToYEarAndAboveGivenPrice() throws MyException {
        System.out.println("PLEASE PROVIDE PRICE ABOVE WHICH PRODUCTS WILL BE FOUND");
        Integer price = Integer.parseInt(sc.nextLine());
        System.out.println("PLEASE PROVIDE STARTING YEAR");
        LocalDate dateStart = LocalDate.of(Integer.parseInt(sc.nextLine()), 1, 1);
        System.out.println("PLEASE PROVIDE LAST YEAR");
        LocalDate dateEnd = LocalDate.of(Integer.parseInt(sc.nextLine()), 12, 31);

        customerOrdersRepository.findAll().stream()
                .map(customerOrder -> myMapper.fromCustomerOrderToCustomerOrderDto(customerOrder))
                .filter(customerOrderDto -> customerOrderDto.getDate().isAfter(dateStart)
                        && customerOrderDto.getDate().isBefore(dateEnd) && calculateFinalPrice(customerOrderDto).compareTo(BigDecimal.valueOf(price)) > 0)
                .forEach(customerOrderDto -> System.out.println("PRODUCT: " + customerOrderDto.getProductDto().getName() +
                        ", CUSTOMER: " + customerOrderDto.getCustomerDto().getName() +
                        " " + customerOrderDto.getCustomerDto().getSurname() + ", PRICE: "
                        + calculateFinalPrice(customerOrderDto) + " QUANTITY: " + customerOrderDto.getQuantity() +
                        ", DATE OF ORDER: " + customerOrderDto.getDate()));
    }

    @Override
    public void findProductsOrderedBy() throws MyException {
        CustomerDto customerDto = findCustomer();

        customerOrdersRepository.findAll().stream()
                .map(customerOrder -> myMapper.fromCustomerOrderToCustomerOrderDto(customerOrder))
                .filter(customerOrderDto -> customerOrderDto.getCustomerDto().equals(customerDto))
                .sorted(Comparator.comparing(customerOrderDto -> customerOrderDto.getProductDto().getProducerDto().getName()))
                .forEach(customerOrderDto -> System.out.println("PRODUCT NAME: " + customerOrderDto.getProductDto().getName()
                        + ", QUANTITY:" + customerOrderDto.getQuantity() + ", DATE: " + customerOrderDto.getDate()
                        + ", PRICE: " + calculateFinalPrice(customerOrderDto)));
    }

    @Override
    public void findCustomersWhichOrderedProductFromTheirCountry() throws MyException {
        customerOrdersRepository.findAll().stream()
                .map(customerOrder -> myMapper.fromCustomerOrderToCustomerOrderDto(customerOrder))
                .filter(customerOrderDto -> customerOrderDto.getCustomerDto().getCountryDto().getName().equals(customerOrderDto.getProductDto().getProducerDto().getCountryDto().getName()))
                .map(CustomerOrderDto::getCustomerDto)
                .forEach(customerDto -> System.out.println("CUSTOMER: " + customerDto.getName() +
                        " " + customerDto.getSurname() + ", COUNTRY: " + customerDto.getCountryDto().getName()
                        + ", AGE: " + customerDto.getAge() + ", ORDERS OF PRODUCTS FROM FOREIGN COUNTRIES: " +
                        customerOrdersRepository.findAll().stream()
                                .map(customerOrder -> myMapper.fromCustomerOrderToCustomerOrderDto(customerOrder))
                                .filter(customerOrderDto -> !customerDto.getCountryDto().getName().equals(customerOrderDto.getProductDto().getProducerDto().getName()) &&
                                        customerOrderDto.getCustomerDto().equals(customerDto))
                                .count()));
    }
}
