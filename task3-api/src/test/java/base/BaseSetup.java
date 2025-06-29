package base;

import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.BeforeMethod;

public class BaseSetup {

    protected SoftAssertions softAssert;
    protected BaseApi baseApi;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        softAssert = new SoftAssertions();
        baseApi = new BaseApi();
    }
}
