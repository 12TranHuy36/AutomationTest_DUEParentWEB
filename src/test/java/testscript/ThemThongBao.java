package testscript;



import io.qameta.allure.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import common.BaseTest;
import data.ReadDataProvider;
import page.QuanLyThongBaoPage;

@Epic("Quản lý thông báo")
@Feature("Thêm thông báo")
public class ThemThongBao extends BaseTest{

    @BeforeMethod
    public void setUp() throws IOException {
        setupDriver();
        driver.get("https://localhost:7011/");
    }

    @Test(description ="Verify behavior of adding new notice successfully from screen 'thông báo'",
            dataProvider = "AccountAndNotificationData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra việc thêm mới một thông báo thành công")
    public  void testScript1(String user, String pass,String td, String nd){
        Allure.step("1. Đăng nhập vào acc "+user);
        login(user,pass);

        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);

        Allure.step("2. Điều hướng tới màn hình thông báo");
        ql.navigationTB();

        Allure.step("3. Click vào icon Thêm");
        ql.clickIconThem();
        try{
            Allure.step("4. Xác minh popup Thêm được hiển thị");
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup thêm không mở");

            Allure.step("5. Nhập tiêu đề và nội dung");
            ql.enterTieuDe(td);
            ql.enterNoidung(nd);

            Allure.step("6. Nhấn nút Lưu");
            ql.clickButtonLuu();
            sleep(1500);

            Allure.step("7. Xác thực thông báo hiển thị đầu danh sách");
            String tb = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='announce-detail'][1]//a"))).getText();
            Assert.assertTrue(tb.contains(td),"Tiêu đề thông báo đầu ở đầu danh sách không giống với thông báo mới thêm");

        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Test(description = "Verify that the function inserts links into the new notification successfully",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra chức năng chèn liên kết vào thông báo mới thành công")
    public void testScript2(String user, String pass) throws IOException {
        loadJsonFile("src/test/resources/notification.json");
        Allure.step("1. Đăng nhập vào acc "+user);
        login(user,pass );

        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);

        Allure.step("2. Điều hướng tới màn hình thông báo");
        ql.navigationTB();

        Allure.step("3. Click vào icon Thêm");
        ql.clickIconThem();

        Allure.step("4. Nhập tiêu đề và nội dung");
        ql.enterTieuDe(readJson("$.thongBao[1].tieuDe"));
        ql.enterNoidung(readJson("$.thongBao[1].noiDung"));

        Allure.step("5. Dùng JavaScript để bôi đen nội dung vừa nhập");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "const range = document.createRange();" + //Tạo một vùng chọn (range) mới.
                        "const editor = document.querySelector('#editor p');" + //Lấy đoạn <p> trong vùng soạn thảo.
                        "const textNode = editor.firstChild;" + //Lấy nội dung văn bản đầu tiên bên trong thẻ <p> (dưới dạng TextNode).
                        "const startIndex = textNode.textContent.indexOf('Gán link facebook');" + //Tìm vị trí bắt đầu của chuỗi "Gán link facebook" trong đoạn văn bản.
                        "const endIndex = startIndex + 'Gán link facebook'.length;" + //Tính vị trí kết thúc của chuỗi đó.
                        "range.setStart(textNode, startIndex);" + //Đặt điểm bắt đầu và kết thúc vùng chọn (range) từ chuỗi đã tìm.
                        "range.setEnd(textNode, endIndex);" +
                        "const sel = window.getSelection();" + //Xóa vùng chọn hiện tại (nếu có), rồi áp dụng vùng chọn mới.
                        "sel.removeAllRanges();" +
                        "sel.addRange(range);"
        );
        Allure.step("6. Click vào icon chèn link (giả sử có nút chèn link trong thanh công cụ) và Nhập URL vào hộp thoại chèn link");
        ql.clickAndInsertLink("https://www.facebook.com/");

        Allure.step("7. Nhấn vào button Lưu");
        ql.clickButtonLuu();
        sleep(2000);

        Allure.step("8. Click vào thông báo mới được thêm");
        ql.clickTBmoi();
        try{
            Allure.step("9. Kiểm tra link gán vào có đúng ở màn hình chi tiết thông báo không");
            WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p//a[@target='_blank']")));
            String l = link.getAttribute("href");
            Assert.assertEquals(l,"https://www.facebook.com/","Link chèn vào không khớp");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description ="Verify that the 'Không được để trống!' error message is displayed when the user leaves the title and content fields blank in the notification creation form",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra thông báo lỗi 'Không được để trống!' được hiển thị khi người dùng để trống trường tiêu đề và nội dung trong biểu mẫu tạo thông báo")
    public  void testScript3(String user, String pass){
        login(user,pass);
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        ql.navigationTB();
        Allure.step("1. CLick vào icon thêm từ màn hình thông báo");
        ql.clickIconThem();
        try{
            Allure.step("2. Xác thực xem có hiển thị popup thêm thông báo không");
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup thêm không mở");
            Allure.step("3. Nhấn vào button Lưu");
            ql.clickButtonLuu();
            sleep(1500);
            Allure.step("4. Kiểm tra hiển thị thông báo lỗi 'Không được để trống!' bên dưới các trường nhập");
            Assert.assertTrue(ql.checkDisplayNoBlank(),"Có trường nhập bắt buộc không hiển thị thông báo lỗi 'Không được để trống!' bên dưới ");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate that there is no limit to the number of characters entered when the user enters 500 characters in the title textbox and 2000 character in the content textbox",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra không có giới hạn về số ký tự được nhập khi người dùng nhập 500 ký tự vào hộp văn bản tiêu đề và 2000 ký tự vào hộp văn bản nội dung")
    public void testScript4(String user, String pass){
        login(user,pass);
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        ql.navigationTB();
        Allure.step("1. CLick vào icon thêm từ màn hình thông báo");
        ql.clickIconThem();
        try{
            Allure.step("2. Xác thực xem có hiển thị popup thêm thông báo không");
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup thêm không mở");
            Allure.step("3. Nhập giá trị tiêu đề thông báo");
            String td = taoChuoiKyTu(500);
            ql.enterTieuDe(td);
            Allure.step("4. Nhập giá trị hợp lệ vào nội dung chi tiết thông báo");
            String nd = taoChuoiKyTu(2000);
            ql.enterNoidung(nd);
            Allure.step("5. Nhấn vào button Lưu");
            ql.clickButtonLuu();
            sleep(2000);
            Allure.step("6. Kiểm tra thông báo được thêm thành công ở màn hình thông báo không");
            String tb = ql.checkTDTB();
            Assert.assertEquals(tb,td,"Tiêu đề thông báo đầu ở đầu danh sách không giống với thông báo mới thêm");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate that the limit on the number of characters entered when the user enters 501 characters in the title text box",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra giới hạn về số ký tự được nhập khi người dùng nhập 501 ký tự vào hộp văn bản tiêu đề")
    public void testScript5(String user, String pass){
        login(user,pass);
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        ql.navigationTB();
        Allure.step("1. CLick vào icon thêm từ màn hình thông báo");
        ql.clickIconThem();
        try{
            Allure.step("2. Xác thực xem có hiển thị popup thêm thông báo không");
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup thêm không mở");
            Allure.step("3. Nhập giá trị tiêu đề thông báo");
            String td = taoChuoiKyTu(501);
            ql.enterTieuDe(td);
            Allure.step("4. Nhập giá trị hợp lệ vào nội dung chi tiết thông báo");
            String nd = taoChuoiKyTu(2000);
            ql.enterNoidung(nd);
            Allure.step("5. Nhấn vào button Lưu");
            ql.clickButtonLuu();
            sleep(2000);
            Allure.step("6. Kiểm tra thông báo được thêm có số lượng kí tự có bị giới hạn chỉ có 500 kí tự không");
            String tb = ql.checkTDTB();
            Assert.assertNotEquals(tb.length(),td.length(),"Tiêu đề thông báo đầu ở đầu danh sách không giống với thông bá");

        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Validate that the limit on the number of characters entered when the user enters 2001 characters in the content text box",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra giới hạn về số ký tự được nhập khi người dùng nhập 2001 ký tự vào hộp văn bản nội dung")
    public void testScript6(String user, String pass){
        login(user,pass);
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        ql.navigationTB();
        Allure.step("1. CLick vào icon thêm từ màn hình thông báo");
        ql.clickIconThem();
        try{
            Allure.step("2. Xác thực xem có hiển thị popup thêm thông báo không");
            String popupDisplay = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='xmas-popup']"))).getCssValue("display");
            Assert.assertEquals(popupDisplay, "block", "Popup thêm không mở");
            Allure.step("3. Nhập giá trị hợp lệ vào tiêu đề thông báo");
            String td = taoChuoiKyTu(500);
            ql.enterTieuDe(td);
            Allure.step("4. Nhập giá trị 2001 lý tự vào chi tiết thông báo");
            String nd = taoChuoiKyTu(2001);
            ql.enterNoidung(nd);
            Allure.step("5. Nhấn vào button Lưu");
            ql.clickButtonLuu();
            sleep(2000);
            Allure.step("6. Kiểm tra hiển thị thông báo không được nhập quá 2000 ký tự tại popup thêm");
            String tbNd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='errorNoiDng']"))).getText().trim();
            Assert.assertEquals(tbNd,"Nội dung không được vượt quá 2000 ký tự!","Không hiển thị thông báo lỗi mặt dù nhập 2001 ký tự vượt quá giới hạn cho phép");
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

