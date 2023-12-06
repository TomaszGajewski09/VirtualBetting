package com.projekt.VirtualBetting.model.types;

import com.projekt.VirtualBetting.exception.InvalidEnumTypeException;

public enum SelectedOption {
    HOME, DRAW, AWAY;

    public static boolean isValid(String name) {
        for (SelectedOption option : SelectedOption.values()) {
            if (option.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static SelectedOption getIfValid(String name) {
        if (isValid(name)) {
            return SelectedOption.valueOf(name.toUpperCase());
        } else {
            throw new InvalidEnumTypeException("SelectedOption: " + name);
        }

    }
}
