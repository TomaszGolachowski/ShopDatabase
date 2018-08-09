package com.app.tGolachowski.service;

import com.app.tGolachowski.dto.*;
import com.app.tGolachowski.entity.Country;
import com.app.tGolachowski.entity.Trade;
import com.app.tGolachowski.parsers.*;

import java.util.List;

public class DataInitializerServiceImpl implements DataInitializerService {

    private MyService myService = new MyServiceImpl();

    @Override
    public void initialize() {
        List<CategoryDto> categoryDtos = Parser.parseFile(Filenames.CATEGORY, new CategoryParser());
        for (CategoryDto categoryDto : categoryDtos) {
            myService.addCategory(categoryDto);
        }

        List<CountryDto> countryDtos = Parser.parseFile(Filenames.COUNTRIES, new CountryParser());
        for (CountryDto countryDto : countryDtos) {
            myService.addCountry(countryDto);
        }

        List<CustomerDto> customerDtos = Parser.parseFile(Filenames.CUSTOMERS, new CustomerParser());
        for (CustomerDto customerDto : customerDtos) {
            myService.addCustomer(customerDto);
        }

        List<TradeDto> tradeDtos = Parser.parseFile(Filenames.TRADES, new TradeParser());
        for (TradeDto tradeDto : tradeDtos) {
            myService.addTrade(tradeDto);
        }

        List<ShopDto> shopDtos = Parser.parseFile(Filenames.SHOPS, new ShopParser());
        for (ShopDto shopDto : shopDtos) {
            myService.addShop(shopDto);
        }

        List<ProducerDto> producerDtos = Parser.parseFile(Filenames.PRODUCERS, new ProducerParser());
        for (ProducerDto producerDto: producerDtos){
            myService.addProducer(producerDto);
        }

        List<ProductDto> productDtos = Parser.parseFile(Filenames.PRODUCTS, new ProductParser());
        for (ProductDto productDto: productDtos){
            myService.addProduct(productDto);
        }

        List<StockDto> stockDtos = Parser.parseFile(Filenames.STOCKS,new StocksParser());
        for (StockDto stockDto: stockDtos){
            myService.addStock(stockDto);
        }

        List<CustomerOrderDto> customerOrderDtos = Parser.parseFile(Filenames.CUSTOMER_ORDERS,new CustomerOrderParser());
        for (CustomerOrderDto customerOrderDto: customerOrderDtos){
            myService.addCustomerOrder(customerOrderDto);
        }
    }

}


