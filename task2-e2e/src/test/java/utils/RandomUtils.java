package utils;

import java.util.Random;

public class RandomUtils {

    private static final Random RANDOM = new Random();

    public static int getTwoDigitRandomNumber() {
        return RANDOM.nextInt(90) + 10;
    }
}