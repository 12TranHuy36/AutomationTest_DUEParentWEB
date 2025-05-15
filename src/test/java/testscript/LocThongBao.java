package testscript;

import common.BaseTest;
import data.ReadDataProvider;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.QuanLyThongBaoPage;

import java.util.List;

public class LocThongBao extends BaseTest {
    @BeforeMethod
    public void setUp() {
        setupDriver();
        driver.get("https://localhost:7011/");
    }

    @Test (description = "Verify that filtering notifications by status updates the displayed list correctly",
            dataProvider = "AccountAndFilterStatusData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra việc lọc thông báo theo trạng thái cập nhật danh sách hiển thị một cách chính xác")
    public void testScript16(String user, String pass, String value, String label){
        Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
        login(user, pass);
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        Allure.step("2. Điều hướng đến màn hình quản lý thông báo ");
        ql.navigationQLTB();
        Allure.step("3. Click vào icon lọc thông báo");
        ql.clickIconLocQLTB();
        sleep(1000);
        try{
            Allure.step("4. Xác thực xem có hiển thị popup lọc thông báo không");
            String popupFilter = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup-filter-2']"))).getCssValue("display");
            Assert.assertEquals(popupFilter, "block", "Popup lọc không mở");
            Allure.step("5. Click chọn giá trị lọc tại droplist trạng thái và lọc");
            ql.filterByStatus(value);
            sleep(1000);
            Allure.step("6. Duyệt danh sách kiểm tra kết quả trả về có đúng với điều kiện lọc không");
            boolean result = false;
            List<WebElement> listElements = driver.findElements(By.xpath("//div[@id='announce-date']"));
            int total = listElements.size();
            int countck = 0;
            int countan = 0;
            for (int index = 1; index <= total; index++) {
                if(ql.checkStatusTB(index)) {
                    countck++; // true cong khai
                }else{
                    countan++;// flase an
                }
            }
            if (label.equals("Công khai") && countck==total){
                result = true;
            } else if (label.equals("Ẩn") && countan==total) {
                result = true;
            }
            Assert.assertTrue(result,"Danh sách thông báo sau khi lọc có kết quả không khớp với điều kiện lọc");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test (description = "Verify that filtering notifications by person create updates the displayed list correctly",
            dataProvider = "AccountAndFilterPersonCreateData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra việc lọc thông báo theo người đăng cập nhật danh sách hiển thị một cách chính xác")
    public void testScript17(String user, String pass, String value, String label){
        Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
        login(user, pass);
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        Allure.step("2. Điều hướng đến màn hình quản lý thông báo ");
        ql.navigationTB();
        Allure.step("3. Click vào icon lọc thông báo");
        ql.clickIconLocTB();
        sleep(1000);
        try{
            Allure.step("4. Xác thực xem có hiển thị popup lọc thông báo không");
            String popupFilter = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup-filter']"))).getCssValue("display");
            Assert.assertEquals(popupFilter, "block", "Popup lọc không mở");
            Allure.step("5. Click chọn giá trị lọc tại droplist người tạo và lọc");
            ql.filterByPersonCreate(value);
            sleep(1000);
            Allure.step("6. Duyệt danh sách kiểm tra kết quả trả về có đúng với điều kiện lọc không");
            boolean result = false;
            List<WebElement> listElements = driver.findElements(By.xpath("//div[@id='announce-detail']//a//small"));
            int total = listElements.size();
            int count = 0;
            for (int index = 1; index <= total; index++) {
                if(ql.nguoiDang(index).contains(label)) {
                    count++;
                }
            }
            if (count==total){
                result = true;
            }
            Assert.assertTrue(result,"Danh sách thông báo sau khi lọc có kết quả không khớp với điều kiện lọc");

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
