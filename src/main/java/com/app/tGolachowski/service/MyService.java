package com.app.tGolachowski.service;


import com.app.tGolachowski.dto.*;
import com.app.tGolachowski.entity.GuaranteeComponent;
import com.app.tGolachowski.entity.Stock;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface MyService {

    void addCountry(CountryDto countryDto);
    void addCountry(String name);
    CountryDto findCountryById(Long id);
    CountryDto findCountryByName(String name);

    void addCustomer(CustomerDto customerDto);
    CustomerDto findCustomer();
    CustomerDto findCustomerById(Long id);
    CustomerDto findCustomerByNameSurnameAndCountry(CustomerDto customerDto);
    Set<CustomerDto> findAllCustomers();

    void showCustomerOrder();
    void addCustomerOrder(CustomerOrderDto customerOrderDto);
    CustomerOrderDto findCustomerOrderById(Long id);

    void addProduct(ProductDto productDto);
    ProductDto findProductById(Long id);
    ProductDto findProductByNameAndCategory(String name, String categoryName);

    ProductDto findProductByName(String name);
    Set<ProductDto> findAllProducts();

    void addCategory(CategoryDto categoryDto);
    CategoryDto findCategoryByName(String name);
    CategoryDto findCategoryById(Long id);

    void addProducer(ProducerDto producerDto);
    Set<ProducerDto> findAllProducers();
    ProducerDto findProducerById(Long id);
    ProducerDto findProducerByName(String name);

    void addTrade(TradeDto tradeDto);
    void addTrade(String name);
    TradeDto findTradeDtoById(Long id);
    TradeDto findTradeByName(String name);

    void addStock(StockDto stockDto);
    Set<StockDto> findAllStocks();
    StockDto findStockById(Long id);

    Stock checkStockValidation(StockDto stockDto);
    Boolean checkIfStocksHaveEnoughProducts(CustomerOrderDto customerOrderDto);
    void updateStockAfterOrder(CustomerOrderDto customerOrderDto);

    void addShop(ShopDto shopDto);
    ShopDto findShopByName(String name);
    ShopDto findShopById(Long id);
    List<ShopDto> findAllShops();

    void addError(ErrorDto errorDto);

    void theMostExpensiveInEachCategory();

    String countHowManyOrders(ProductDto productDto);

    List<ProductDto> findProductsByAgeAndDemographics();

    void reportByAgeAndDemographics();

    void viableWarranty();

    Set<GuaranteeComponent> generateGuaranteeComponents();

    void checkShopsForForeignProducts();

    Set<CountryDto> checkForDepartments(ShopDto shopDto);

    void findProducersWhichProducedMoreThan();

    BigDecimal calculateFinalPrice(CustomerOrderDto customerOrderDto);

    void findProductsFromYearToYEarAndAboveGivenPrice();

    void findProductsOrderedBy();

    void findCustomersWhichOrderedProductFromTheirCountry();

}
