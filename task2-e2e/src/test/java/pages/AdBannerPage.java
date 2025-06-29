package pages;

import base.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.yaml.snakeyaml.Yaml;
import utils.ElementUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class AdBannerPage extends BasePage {
    private final AppiumDriver driver;

    @AndroidFindBy(id = "buttonClose")
    private WebElement closeButton;

    @AndroidFindBy(id = "buttonContinue")
    private WebElement continueButton;

    Map<String, Object> config = null;

    public AdBannerPage(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void closeADBanner() {
        try {
            config = new Yaml().load(new FileReader("config/config.yaml"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //To get through getting started banners
        for (int i = 0; i < 3; i++) {
            ElementUtils.waitForStableElement(driver, () -> continueButton, (Integer) config.get("staleElementWait")).click();
        }
        closeButton.click();
        log.info("dismissed getting started and AD banners");
    }

}
