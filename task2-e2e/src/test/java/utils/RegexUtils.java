package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    /**
     * Extracts the signed number from a string using the regex (-?)\$?\s*(\d+).
     * Returns the signed number as a string (e.g. "-530", "45"), or empty if no match.
     */
    public static String extractSignedNumber(String input) {
        Pattern pattern = Pattern.compile("(-?)\\$?\\s*(\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            String sign = matcher.group(1);   // '-' or empty
            String number = matcher.group(2); // digits only
            return sign + number;
        }
        return "";
    }
}
