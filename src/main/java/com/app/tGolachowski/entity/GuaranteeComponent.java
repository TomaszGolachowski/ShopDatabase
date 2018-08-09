package com.app.tGolachowski.entity;

import javax.persistence.Table;

@Table(name = "guarantee_component")
public enum  GuaranteeComponent {
    HELP_DESK, MONEY_BACK, SERVICE, EXCHANGE
}
