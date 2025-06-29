package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Supplier;

public class ElementUtils {

    /**
     * Waits for a stable (non-stale) WebElement using WebDriverWait.
     *
     * @param driver          the WebDriver instance
     * @param elementSupplier a lambda that supplies a fresh WebElement reference
     * @param timeoutSeconds  maximum time to wait
     * @return a stable WebElement
     */
    public static WebElement waitForStableElement(AppiumDriver driver, Supplier<WebElement> elementSupplier, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        return wait.until((ExpectedCondition<WebElement>) drv -> {
            try {
                WebElement element = elementSupplier.get();
                // Access a method to verify it's not stale
                if (element != null && element.isDisplayed()) {
                    return element;
                }
                return null;
            } catch (StaleElementReferenceException | NullPointerException e) {
                return null;
            }
        });
    }

    /**
     * Taps a single digit on the app's custom numeric keyboard.
     * <p>
     * This method dynamically constructs the resource ID for the keyboard button
     * corresponding to the specified number and performs a click on it.
     *
     * @param driver The Appium driver instance used to locate and click the keyboard button.
     * @param number The digit (0–9) to be tapped on the keyboard.
     *               Assumes the keyboard button ID format is:
     *               "com.monefy.app.lite:id/buttonKeyboardX", where X is the number.
     */
    public static void tapKeyboardNumber(AppiumDriver driver, int number) {
        String dynamicId = "com.monefy.app.lite:id/buttonKeyboard" + number;
        WebElement key = driver.findElement(By.id(dynamicId));
        key.click();
    }

    /**
     * Taps each digit of a multi-digit number on the app's custom numeric keyboard.
     * <p>
     * This method breaks the number into individual digits and taps each digit
     * by calling {@link #tapKeyboardNumber(AppiumDriver, int)}.
     *
     * @param driver The Appium driver instance used to interact with the keyboard.
     * @param number The full integer (e.g., 123) to be tapped as a sequence of digits.
     */
    public static void tapFullNumber(AppiumDriver driver, int number) {
        String digits = String.valueOf(number);
        for (char digitChar : digits.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            tapKeyboardNumber(driver, digit);
        }
    }
}
