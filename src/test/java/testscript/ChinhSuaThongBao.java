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

import java.io.IOException;
import java.util.List;

public class ChinhSuaThongBao extends BaseTest {

    @BeforeMethod
    public void setUp(){
        setupDriver();
        driver.get("https://localhost:7011/");
    }

    @Test(description = "Verify that the user successfully edited the content and title of the notification they posted",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra người dùng đã chỉnh sửa thành công nội dung và tiêu đề của thông báo mà họ đã đăng")
    public void testScript14(String user, String pass){
        Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
        login(user, pass);
        QuanLyThongBaoPage ql =new QuanLyThongBaoPage(driver);
        String td = "Chỉnh sửa tiêu đề";
        String nd = "Chỉnh sửa nội dung";
        Allure.step("2. Điều hướng đến màn hình quản lý thông báo");
        ql.navigationQLTB();

        Allure.step("3. Tìm thông báo do do chính acc đó đăng");
        String tenacc = "From: "+ wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='name']//b"))).getText();
        String check = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='announce-detail'][1]//a//small"))).getText();
        if(tenacc.equals(check)){
            Allure.step("4. Click vào xem chi tiết thông báo đó");
            ql.clickXemTBCT(1);
        }else{
            Allure.step("4. Click vào xem chi tiết thông báo đó");
            List<WebElement> listElements = driver.findElements(By.xpath("//div[@id='announce-detail']//a//small"));
            int total = listElements.size();
            for (int index = 1; index <= total; index++) {
                String from =ql.nguoiDang(index);
                if (from.equals("From: Quản trị viên")) {
                    ql.clickXemTBCT(index);
                    break;
                }
            }
        }
        Allure.step("5. Click vào icon chỉnh sửa");
        ql.clickIconEdit();
        try{
            Allure.step("6. Xác thực xem có hiển thị popup chỉnh sửa thông báo không");
            sleep(1000);
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup thêm không mở");
            Allure.step("7. Chỉnh sửa tiêu đề nội dung");
            ql.enterTieuDe(td);
            ql.enterNoidung(nd);
            Allure.step("8. Ấn lưu");
            ql.clickButtonLuu();
            sleep(1500);
            Allure.step("9. Kiểm tra tiêu đề, nội dung nhập đã được chỉnh sửa ở bên ngoài man hình chi tiết thông báo đó");
            Assert.assertTrue(ql.checkTdNdEdit(td,nd),"Tiêu đề và nội dung thông báo chỉnh sửa không được cập nhật");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    @Test( description ="Verify that the 'Không được để trống!' error message is displayed when the user leaves the title and content fields blank in the notification edit form",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra thông báo lỗi 'Không được để trống!' được hiển thị khi người dùng để trống trường tiêu đề và nội dung trong biểu mẫu chỉnh sửa thông báo")
    public  void testScript15(String user, String pass){
        Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
        login(user,pass);
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        Allure.step("2. Điều hướng đến màn hình quản lý thông báo ");
        ql.navigationQLTB();
        Allure.step("3. Tìm thông báo do do chính acc đó đăng");
        String tenacc = "From: "+ wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='name']//b"))).getText();
        String check = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='announce-detail'][1]//a//small"))).getText();
        if(tenacc.equals(check)){
            Allure.step("4. Click vào xem chi tiết thông báo đó");
            ql.clickXemTBCT(1);
        }else{
            Allure.step("4. Click vào xem chi tiết thông báo đó");
            List<WebElement> listElements = driver.findElements(By.xpath("//div[@id='announce-detail']//a//small"));
            int total = listElements.size();
            for (int index = 1; index <= total; index++) {
                String from =ql.nguoiDang(index);
                if (from.equals("From: Quản trị viên")) {
                    ql.clickXemTBCT(index);
                    break;
                }
            }
        }
        Allure.step("5. Bấm vào icon chỉnh sửa");
        ql.clickIconEdit();
        sleep(1000);
        try{
            Allure.step("6. Xác thực xem có hiển thị popup chỉnh sửa thông báo không");
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup chỉnh sửa không mở");
            Allure.step("7. Chỉnh sửa lại giá trị tiêu đề thông báo");
            ql.enterTieuDe("");
            Allure.step("8. Chỉnh sửa lại giá trị nội dung chi tiết thông báo");
            ql.enterNoidung("");
            Allure.step("9. Nhấn vào button Lưu");
            ql.clickButtonLuu();
            sleep(1500);
            Allure.step("10. Kiểm tra thông báo kiểm tra hiển thị thông báo lỗi 'Không được để trống!' bên dưới các trường nhập");
            Assert.assertTrue(ql.checkDisplayNoBlank(),"Không hiên thị thông báo lỗi 'Không được để trống!' bên dưới các trường nhập");
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
