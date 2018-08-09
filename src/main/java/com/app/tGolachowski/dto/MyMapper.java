package com.app.tGolachowski.dto;

import com.app.tGolachowski.entity.*;
import com.app.tGolachowski.entity.Error;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MyMapper {

    public Customer fromCustomerDtoToCustomer(CustomerDto customerDto) {

        return customerDto == null ? null : Customer.builder()
                .id(customerDto.getId())
                .age(customerDto.getAge())
                .country(customerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(customerDto.getCountryDto()))
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .build();
    }

    public CustomerDto fromCustomerToCustomerDto(Customer customer) {

        return CustomerDto.builder()
                .id(customer.getId())
                .age(customer.getAge())
                .name(customer.getName())
                .surname(customer.getSurname())
                .CountryDto(customer.getCountry() == null ? null : fromCountryToCountryDto(customer.getCountry()))
                .build();
    }

    public List<CustomerDto> customerDtoconvertToDtoList(List<Customer> customers) {
        return customers.stream()
                .map(customer -> fromCustomerToCustomerDto(customer))
                .collect(Collectors.toList());

    }

    public List<ProducerDto> producerDtoconvertToDtoList(List<Producer> producers) {
        return producers.stream()
                .map(producer -> fromProducerToProducerDto(producer))
                .collect(Collectors.toList());
    }

    public List<ProductDto> productDtoconvertToDtoList(List<Product> products) {
        return products.stream()
                .map(product -> fromProductToProductDto(product))
                .collect(Collectors.toList());
    }

    public Country fromCountryDtoToCountry(CountryDto countryDto) {
        return countryDto == null ? null : Country.builder()
                .id(countryDto.getId())
                .name(countryDto.getName())
                .build();
    }

    public CountryDto fromCountryToCountryDto(Country country) {
        return country == null ? null : CountryDto.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }

    public CustomerOrder fromCustomerOrderDtoToCustomerOrder(CustomerOrderDto customerOrderDto) {

        return customerOrderDto == null ? null : CustomerOrder.builder()
                .id(customerOrderDto.getId())
                .product(customerOrderDto.getProductDto() == null ? null : fromProductDtoToProduct(customerOrderDto.getProductDto()))
                .customer(customerOrderDto.getCustomerDto() == null ? null : fromCustomerDtoToCustomer(customerOrderDto.getCustomerDto()))
                .ePayments(customerOrderDto.getEPayment())
                .quantity(customerOrderDto.getQuantity())
                .date(customerOrderDto.getDate())
                .discount(customerOrderDto.getDiscount())
                .build();

    }

    public CustomerOrderDto fromCustomerOrderToCustomerOrderDto(CustomerOrder customerOrder) {
        return customerOrder == null ? null : CustomerOrderDto.builder()
                .id(customerOrder.getId())
                .customerDto(customerOrder.getCustomer() == null ? null : fromCustomerToCustomerDto(customerOrder.getCustomer()))
                .date(customerOrder.getDate())
                .discount(customerOrder.getDiscount())
                .quantity(customerOrder.getQuantity())
                .productDto(customerOrder.getProduct() == null ? null : fromProductToProductDto(customerOrder.getProduct()))
                .ePayment(customerOrder.getEPayments())
                .build();
    }


    public ProductDto fromProductToProductDto(Product product) {
        return product == null ? null : ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryDto(product.getCategory() == null ? null : fromCategoryToCategoryDto(product.getCategory()))
                .producerDto(product.getProducer() == null ? null : fromProducerToProducerDto(product.getProducer()))
                .guaranteeComponentSet(product.getGuaranteeComponentSet())
                .build();
    }

    public Product fromProductDtoToProduct(ProductDto productDto) {
        return productDto == null ? null : Product.builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .customerOrders(new HashSet<>())
                .category(productDto.getCategoryDto() == null ? null : fromCategoryDtoToCategory(productDto.getCategoryDto()))
                .producer(productDto.getProducerDto() == null ? null : fromProducerDtoToProducer(productDto.getProducerDto()))
                .guaranteeComponentSet(productDto.getGuaranteeComponentSet())
                .build();
    }

    public Category fromCategoryDtoToCategory(CategoryDto categoryDto) {
        return categoryDto == null ? null : Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public CategoryDto fromCategoryToCategoryDto(Category category) {
        return category == null ? null : CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public Producer fromProducerDtoToProducer(ProducerDto producerDto) {

        return producerDto == null ? null : Producer.builder()
                .id(producerDto.getId())
                .name(producerDto.getName())
                .country(producerDto.getCountryDto() == null ? null : fromCountryDtoToCountry(producerDto.getCountryDto()))
                .name(producerDto.getName())
                .trade(producerDto.getTradeDto() == null ? null : fromTradeDtoToTrade(producerDto.getTradeDto()))
                .build();

    }

    public ProducerDto fromProducerToProducerDto(Producer producer) {
        return producer == null ? null : ProducerDto.builder()
                .id(producer.getId())
                .name(producer.getName())
                .countryDto(producer.getCountry() == null ? null : fromCountryToCountryDto(producer.getCountry()))
                .tradeDto(producer.getTrade() == null ? null : fromTradeToTradeDto(producer.getTrade()))
                .name(producer.getName())
                .build();
    }

    public Trade fromTradeDtoToTrade(TradeDto tradeDto) {
        return tradeDto == null ? null : Trade.builder()
                .id(tradeDto.getId())
                .name(tradeDto.getName())
                .build();
    }

    public TradeDto fromTradeToTradeDto(Trade trade) {
        return trade == null ? null : TradeDto.builder()
                .id(trade.getId())
                .name(trade.getName())
                .build();
    }

    public StockDto fromStockToStockDto(Stock stock) {
        return stock == null ? null : StockDto.builder()
                .id(stock.getId())
                .productDto(stock.getProduct() == null ? null : fromProductToProductDto(stock.getProduct()))
                .shopDto(stock.getShop() == null ? null : fromShopToShopDto(stock.getShop()))
                .quantity(stock.getQuantity())
                .build();
    }

    public Stock fromStockDtoToStock(StockDto stockDto) {
        return stockDto == null ? null : Stock.builder()
                .id(stockDto.getId())
                .product(stockDto.getProductDto() == null ? null : fromProductDtoToProduct(stockDto.getProductDto()))
                .shop(stockDto.getShopDto() == null ? null : fromShopDtoToShop(stockDto.getShopDto()))
                .quantity(stockDto.getQuantity())
                .build();
    }

    public Shop fromShopDtoToShop(ShopDto shopDto) {
        return shopDto == null ? null : Shop.builder()
                .id(shopDto.getId())
                .country(shopDto.getCountryDto() == null ? null : fromCountryDtoToCountry(shopDto.getCountryDto()))
                .name(shopDto.getName())
                .build();
    }

    public ShopDto fromShopToShopDto(Shop shop) {
        return shop == null ? null : ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .countryDto(shop.getCountry() == null ? null : fromCountryToCountryDto(shop.getCountry()))
                .build();
    }

    public ErrorDto fromErrorToErrorDto(Error error) {
        return error == null ? null : ErrorDto.builder()
                .message(error.getMessage())
                .date(error.getDate())
                .build();
    }

    public Error fromErrorDtoToError(ErrorDto errorDto) {
        return errorDto == null ? null : Error.builder()
                .message(errorDto.getMessage())
                .date(errorDto.getDate())
                .build();
    }

}



