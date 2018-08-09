package com.app.tGolachowski.entity;

import javax.persistence.Table;

@Table(name = "Payment")
public enum  EPayment {
    CASH, CARD, MONEY_TRANSFER
}