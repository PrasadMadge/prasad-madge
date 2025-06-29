package base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.yaml.snakeyaml.Yaml;
import pages.AdBannerPage;

import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.util.Map;

/**
 * BaseSetup class handles the setup and tear down of the Appium server and AndroidDriver for each test.
 * <p>
 * The Appium server is started once before all tests and stopped after all tests.
 * A fresh AndroidDriver instance is initialized before each test method and closed after each test method.
 * </p>
 */
public class BaseSetup {
    protected AppiumDriver driver;
    private static AppiumDriverLocalService appiumService = null;
    private static final Logger log = LoggerFactory.getLogger(BaseSetup.class);

    Map<String, Object> config = null;
    Map<String, Object> androidConfig = null;
    Map<String, Object> iosConfig = null;
    String platformName = null;

    /**
     * Starts the Appium server once before all test methods in the class.
     * <p>Reads configuration from the YAML file and initializes the AppiumServiceBuilder.</p>
     *
     * @throws Exception if there is an error reading the config or starting the server.
     */
    @BeforeClass
    @SuppressWarnings("unchecked")
    public void startAppiumServer() throws Exception {
        config = new Yaml().load(new FileReader("config/config.yaml"));
        androidConfig = (Map<String, Object>) config.get("android");
        iosConfig = (Map<String, Object>) config.get("ios");
        platformName = (String) config.get("platformName");

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .usingDriverExecutable(new File((String) config.get("nodePath")))
                .withAppiumJS(new File((String) config.get("appiumJS")))
                .usingPort((Integer) config.get("appiumPort"))
                .withIPAddress((String) config.get("appiumServerURL"))
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE);

        appiumService = AppiumDriverLocalService.buildService(builder);
        appiumService.start();
        log.info("Appium server started");
    }

    /**
     * Stops the Appium server after all test methods in the class have run.
     */
    @AfterClass
    public void stopAppiumServer() {
        if (appiumService != null && appiumService.isRunning()) {
            appiumService.stop();
            log.info("Appium server stopped");
        }
    }

    /**
     * Initializes a fresh AndroidDriver instance before each test method.
     * <p>Sets desired capabilities from the loaded configuration and starts the driver session.</p>
     * <p>Closes ad banners or onboarding screens as part of pre-test setup.</p>
     */
    @BeforeMethod
    public void initDriver() {
        DesiredCapabilities caps = new DesiredCapabilities();

        if (platformName.equalsIgnoreCase("Android")) {
            caps.setCapability("platformName", androidConfig.get("platformName"));
            caps.setCapability("appium:deviceName", androidConfig.get("deviceName"));
            caps.setCapability("appium:forceAppLaunch", true);
            caps.setCapability("appium:autoGrantPermissions", true);
            caps.setCapability("appium:automationName", androidConfig.get("automationName"));
            caps.setCapability("appium:app", androidConfig.get("app"));
            caps.setCapability("appium:fullReset", androidConfig.getOrDefault("fullReset", true));
            caps.setCapability("appium:ignoreHiddenApiPolicyError", true);

            driver = new AndroidDriver(appiumService.getUrl(), caps);

        }else if (platformName.equalsIgnoreCase("iOS")) {
            caps.setCapability("platformName", iosConfig.get("platformName"));
            caps.setCapability("appium:deviceName", iosConfig.get("deviceName"));
            caps.setCapability("appium:automationName", iosConfig.get("automationName"));
            caps.setCapability("appium:app", iosConfig.get("app"));
            caps.setCapability("appium:fullReset", iosConfig.getOrDefault("fullReset", false));
            caps.setCapability("appium:noReset", iosConfig.getOrDefault("noReset", true));
            caps.setCapability("appium:useNewWDA", true);

            driver = new IOSDriver(appiumService.getUrl(), caps);
        }else {
            throw new IllegalArgumentException("Unsupported platform: " + platformName);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds((Integer) config.get("implicitWait")));
        log.info("Driver initialized");

        // Dismissing getting started and AD banner
        AdBannerPage adBannerPage = new AdBannerPage(driver);
        adBannerPage.closeADBanner();
    }

    /**
     * Quits the AndroidDriver instance after each test method.
     * <p>Attempts to terminate the app before quitting the driver.</p>
     */
    @AfterMethod
    public void quitDriver() {
        if (driver != null) {
            try {
                if (platformName.equalsIgnoreCase("Android")) {
                    ((AndroidDriver) driver).terminateApp((String) androidConfig.get("appPackage"));
                } else if (platformName.equalsIgnoreCase("iOS")) {
                    ((IOSDriver) driver).terminateApp((String) iosConfig.get("bundleId"));
                }
            } catch (Exception e) {
                log.warn("Error terminating app: {}", e.getMessage());
            }

            driver.quit();
            log.info("Driver quit");
        }
    }

}
