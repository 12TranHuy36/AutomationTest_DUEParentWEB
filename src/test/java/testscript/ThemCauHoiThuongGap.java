package testscript;

import common.BaseTest;
import data.ReadDataProvider;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.CauHoiThuongGapPage;

@Epic("Quản lý câu hỏi thường gặp")
@Feature("Thêm câu hỏi thường gặp")
public class ThemCauHoiThuongGap extends BaseTest {
    @BeforeMethod
    public void setUp(){
        setupDriver();
        driver.get("https://localhost:7011/");
    }

    @Test (description = "Verify that the 'Không được để trống!' error message is displayed when no value is entered in the 3 required input fields",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra thông báo lỗi 'Không được để trống!' được hiển thị khi không có giá trị nào được nhập vào 3 trường nhập bắt buộc")
    public void testScript18(String user, String pass){
        try {
            Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
            login(user, pass);
            CauHoiThuongGapPage ch = new CauHoiThuongGapPage(driver);
            Allure.step("2. Điều hướng đến màn hình các câu hỏi thường gặp");
            ch.navigateCHTG();
            Allure.step("3. Click vào icon thêm");
            ch.clickIconThem();
            Allure.step("4. Xác minh popup Thêm được hiển thị");
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup-CauHoi']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup thêm không mở");
            Allure.step("5. Nhấn lưu");
            ch.clickButtonLuu();
            Allure.step("6. Kiểm tra hiển thị thông báo lỗi 'Không được để trống!' bên dưới các trường nhập");
            Assert.assertTrue(ch.checkDisplayNoBlank(),"Có trường nhập bắt buộc không hiển thị thông báo lỗi 'Không được để trống!' bên dưới ");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify successful FAQ addition with new topic created from FAQ interface",
            dataProvider = "AccountAndFAQData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra việc thêm Câu hỏi thường gặp thành công với chủ đề được tạo mới từ giao diện Câu hỏi thường gặp")
    public void testScript19(String user, String pass, String cauHoi, String cauTraLoi){
        try{
            Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
            login(user, pass);
            CauHoiThuongGapPage ch = new CauHoiThuongGapPage(driver);
            Allure.step("2. Điều hướng đến màn hình các câu hỏi thường gặp");
            ch.navigateCHTG();
            Allure.step("3. Click vào icon thêm");
            ch.clickIconThem();
            Allure.step("4. Thêm chủ đề mới 50 ký tự và chọn nó");
            ch.addChuDeAndChonNo(taoChuoiKyTu(50));
            Allure.step("5. Nhập câu hỏi");
            ch.enterCauHoi(cauHoi);
            Allure.step("6.Nhập câu trả lời");
            ch.enterCauTraLoi(cauTraLoi);
            Allure.step("7. Nhấn lưu");
            ch.clickButtonLuu();
            sleep(1000);
            Allure.step("8. Xác thực câu hỏi thường gặp mới thêm hiển thị đầu danh sách");
            String chtg = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h3[@class='question'][1]"))).getText();
            Assert.assertTrue(chtg.contains(cauHoi),"Câu hỏi thường gặp ở đầu danh sách không giống với câu hỏi thường gặp mới thêm");
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
