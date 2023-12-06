package com.projekt.VirtualBetting.model.types;

import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;

public enum CurrencyType {
    PLN, USD, EUR, GBP, CHF, JPY;

    public static boolean isValid(String name) {
        for (CurrencyType currency : CurrencyType.values()) {
            if (currency.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static CurrencyType getIfValid(String name) {
            if (isValid(name)) {
                return CurrencyType.valueOf(name.toUpperCase());
            } else {
                throw new InvalidEnumTypeException("CurrencyType: " + name);
            }

    }
}
