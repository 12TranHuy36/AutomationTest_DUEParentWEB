package testscript;

import common.BaseTest;
import data.ReadDataProvider;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.CauHoiThuongGapPage;


public class TimKiemCauHoiThuongGap extends BaseTest {
    @BeforeMethod
    public void setUp(){
        setupDriver();
        driver.get("https://localhost:7011/");
    }

    @Test(description = "Verify that the system returns a list of FAQs relevant to the search keyword",
            dataProvider = "AccountAndFAQData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra hệ thống trả về danh sách các câu hỏi thường gặp có liên quan đến từ khóa tìm kiếm")
    public void testScript20(String user, String pass, String cauHoi, String cauTraLoi){
        try{
            Allure.step("1. Đăng nhập vào acc "+user);
            login(user,pass);
            CauHoiThuongGapPage ch = new CauHoiThuongGapPage(driver);
            Allure.step("2. Điều hướng tới màn hình các câu hỏi thường gặp");
            ch.navigateCHTG();
            Allure.step("3. Nhập từ khóa và tìm kiếm");
            ch.Search(cauHoi);
            sleep(1500);
            Allure.step("4. Kiểm tra kết quả danh sách trả về có khớp với từ khóa tìm kiếm không");
            Assert.assertTrue(ch.checkSearchResult(cauHoi),"Danh sách câu hỏi thường gặp trả về không khớp với từ khóa tìm kiếm hoặc không có kết quả");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
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
