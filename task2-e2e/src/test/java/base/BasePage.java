package base;

import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {
    protected final AppiumDriver driver;
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}

