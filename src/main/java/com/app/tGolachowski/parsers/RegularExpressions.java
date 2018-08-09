package com.app.tGolachowski.parsers;

public interface RegularExpressions {
    String CATEGORY_REGEX = "[A-Z ]+";
    String TRADE_REGEX = "[A-Z ]+";
    String COUNTRY_REGEX = "[A-Z ]+";
    String CUSTOMER_REGEX = "[A-Z ]+;[A-Z ]+;\\d.+;[A-Z ]+";
    String SHOP_REGEX = "[A-Z ]+;[A-Z ]+";
    String PRODUCER_REGEX = "[A-Z ]+;[A-Z ]+;[A-Z ]+";
    String PRODUCT_REGEX = "[A-Z ]+;\\d.+;[A-Z ]+;[A-Z ]+";
    String STOCK_REGEX = "[A-Z ]+;[A-Z ]+;\\d+";
    String CUSTOMER_ORDER_REGEX = "[A-Z ]+;[A-Z ]+;[A-Z ]+;[A-Z ]+";
}
