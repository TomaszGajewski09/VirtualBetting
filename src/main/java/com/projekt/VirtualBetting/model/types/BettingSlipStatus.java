package com.projekt.VirtualBetting.model.types;


import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;

public enum BettingSlipStatus {
    WIN, LOSE, PAID;

    public static boolean isValid(String name) {
        for (BettingSlipStatus status : BettingSlipStatus.values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static BettingSlipStatus getIfValid(String name) {
        if (isValid(name)) {
            return BettingSlipStatus.valueOf(name.toUpperCase());
        } else {
            throw new InvalidEnumTypeException("BettingSlipStatus: " + name);
        }

    }
}
