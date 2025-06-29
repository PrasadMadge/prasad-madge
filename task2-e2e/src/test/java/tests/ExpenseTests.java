package tests;

import base.BaseSetup;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.DashboardPage;
import utils.ExpenseCategory;
import utils.RandomUtils;

@Listeners({AllureTestNg.class})
public class ExpenseTests extends BaseSetup {

    @Test(
            description = "Verify that searching filters expenses correctly by category and amount",
            groups = {"expense", "regression"},
            priority = 1
    )
    public void searchToolsFiltersExpenseCorrectly() {
        //Test-data
        int expenseAmount = RandomUtils.getTwoDigitRandomNumber();
        String expenseCategory = ExpenseCategory.getRandomCategory().getValue();

        //Pre-condition
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.createExpenseEntry(expenseAmount, expenseCategory);

        //Test
        dashboardPage.searchExpense(expenseCategory);

        //Assertion
        String actualExpenseAmount = dashboardPage.getExpenseEntryValueIneSearchResults();
        String actualExpenseCategoryName = dashboardPage.getExpenseEntryNameInSearchResults();
        Assert.assertEquals(String.valueOf(expenseAmount), actualExpenseAmount, String.format("Search result category amount was expected: %d, but got: %s", expenseAmount, actualExpenseAmount));
        Assert.assertEquals(expenseCategory, actualExpenseCategoryName, String.format("Search result category name was expected: %s, but got: %s", expenseCategory, actualExpenseCategoryName));
        //TODO add more assertions
    }

    @Test(
            description = "Verify expense is added correctly by category and amount",
            groups = {"expense", "regression"},
            priority = 1
    )
    public void expenseIsAddedCorrectly() {
        //Test-data
        int expenseAmount = RandomUtils.getTwoDigitRandomNumber();
        String expectedBalance = "-" + expenseAmount;

        //Test-Steps
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.createExpenseEntry(expenseAmount, ExpenseCategory.getRandomCategory().getValue());

        //Assertion
        String actualBalance = dashboardPage.getBalanceValue();
        Assert.assertEquals(expectedBalance, actualBalance, String.format("Balance value was expected: %s, but got: %s", expectedBalance, actualBalance));
        //TODO add more assertions
    }

}
