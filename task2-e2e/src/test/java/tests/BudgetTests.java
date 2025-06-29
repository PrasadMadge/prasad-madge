package tests;

import base.BaseSetup;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.SettingsBannerPage;
import utils.RandomUtils;

@Listeners({AllureTestNg.class})
public class BudgetTests extends BaseSetup {

    @Test(
            description = "Verify monthly budget is set correctly ",
            groups = {"budget"},
            priority = 1
    )
    public void monthlyBudgetIsSetCorrectly() {
        //Test-data
        int monthlyBudgetAmount = RandomUtils.getTwoDigitRandomNumber();

        //Test-Steps
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.clickHamburgerMenu();
        dashboardPage.clickSideBarSettingsButton();

        SettingsBannerPage settingsBannerPage = new SettingsBannerPage(driver);
        settingsBannerPage.setMonthlyBudget(monthlyBudgetAmount);

        //Assertion
        String actualBalance = dashboardPage.getBalanceValue();
        Assert.assertEquals(String.valueOf(monthlyBudgetAmount), actualBalance, String.format("Balance value was expected: %d, but got: %s", monthlyBudgetAmount, actualBalance));
        //TODO add more assertions
    }

}
