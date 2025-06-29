package pages;

import base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtils;
import utils.RegexUtils;

public class DashboardPage extends BasePage {
    private final AppiumDriver driver;

    @AndroidFindBy(id = "expense_button")
    private WebElement expenseButton;

    @AndroidFindBy(id = "textViewNote")
    private WebElement noteInputField;

    @AndroidFindBy(id = "keyboard_action_button")
    private WebElement chooseCategoryButton;

    @AndroidFindBy(id = "balance_amount")
    private WebElement balanceAmountText;

    @AndroidFindBy(id = "overflow")
    private WebElement hamburgerMenuButton;

    @AndroidFindBy(id = "settings_textview")
    private WebElement settingsButton;

    @AndroidFindBy(id = "textViewWholeAmount")
    private WebElement expenseEntryAmount;

    @AndroidFindBy(id = "menu_search")
    private WebElement searchButton;

    @AndroidFindBy(id = "et_search")
    private WebElement searchInputTextField;

    @AndroidFindBy(id = "title_text_view")
    private WebElement searchResultListItemName;

    @AndroidFindBy(id = "amount_text_view")
    private WebElement searchResultListItemValue;


    public DashboardPage(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void createExpenseEntry(int expenseAmount, String expenseCategory) {
        expenseButton.click();
        ElementUtils.tapFullNumber(driver, expenseAmount);
        noteInputField.sendKeys(RandomStringUtils.randomAlphanumeric(5));
        String selector = String.format("new UiSelector().text(\"%s\")", expenseCategory);
        chooseCategoryButton.click();
        driver.findElement(AppiumBy.androidUIAutomator(selector)).click();
        log.info("created an expense entry");
    }

    public String getBalanceValue() {
        String balanceText = balanceAmountText.getText();
        return RegexUtils.extractSignedNumber(balanceText);
    }

    public void clickHamburgerMenu() {
        hamburgerMenuButton.click();
    }

    public void clickSideBarSettingsButton() {
        settingsButton.click();
    }

    public void searchExpense(String searchString) {
        searchButton.click();
        searchInputTextField.sendKeys(searchString);
        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    public String getExpenseEntryNameInSearchResults() {
        return searchResultListItemName.getText();
    }

    public String getExpenseEntryValueIneSearchResults() {
        return RegexUtils.extractSignedNumber(searchResultListItemValue.getText());
    }

}
