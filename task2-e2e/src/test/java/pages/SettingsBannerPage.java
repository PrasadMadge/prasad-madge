package pages;

import base.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SettingsBannerPage extends BasePage {

    @AndroidFindBy(id = "is_budget_mode_checkbox")
    private WebElement budgetModeCheckbox;

    @AndroidFindBy(className = "android.widget.EditText")
    private WebElement budgetAmountInputText;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement okButton;


    public SettingsBannerPage(AppiumDriver driver) {
        super(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void setMonthlyBudget(int budgetAmount) {
        budgetModeCheckbox.click();
        budgetAmountInputText.clear();
        budgetAmountInputText.sendKeys(String.valueOf(budgetAmount));
        okButton.click();
        log.info("monthly budget got set");
    }

}
