package utils;

import java.util.Random;

public enum ExpenseCategory {
    BILLS("Bills"),
    CAR("Car"),
    CLOTHES("Clothes"),
    COMMUNICATIONS("Communications"),
    EATING_OUT("Eating out"),
    ENTERTAINMENT("Entertainment"),
    FOOD("Food"),
    GIFTS("Gifts");

    private final String value;

    ExpenseCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    private static final ExpenseCategory[] VALUES = values();
    private static final Random RANDOM = new Random();

    public static ExpenseCategory getRandomCategory() {
        return VALUES[RANDOM.nextInt(VALUES.length)];
    }
}

