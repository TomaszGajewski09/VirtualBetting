package com.projekt.VirtualBetting.model.types;

import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;

public enum TransactionType {
    DEPOSIT, WITHDRAWAL, WIN, LOSS, BET_PLACEMENT, BET_PAYOUT;

    public static boolean isValid(String name) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static TransactionType getIfValid(String name) {
        if (isValid(name)) {
            return TransactionType.valueOf(name.toUpperCase());
        } else {
            throw new InvalidEnumTypeException("TransactionType: " + name);
        }

    }


    public static boolean requiresDeduction(TransactionType type) {
        if (type.equals(WITHDRAWAL) || type.equals(LOSS) || type.equals(BET_PLACEMENT)) {
            return true;
        }
        return false;
    }
}
