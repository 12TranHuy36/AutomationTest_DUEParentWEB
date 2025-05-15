package testscript;

import common.BaseTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class KhoaTaiKhoan extends BaseTest {
    @BeforeMethod
    public void setUp(){
        setupDriver();
        driver.get("https://localhost:7011/");
    }



    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            saveScreenshot(driver);  // <- Capture for Allure
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
