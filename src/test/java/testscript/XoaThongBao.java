package testscript;

import common.BaseTest;
import data.ReadDataProvider;
import io.qameta.allure.*;
import org.openqa.selenium.Alert;
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

@Epic("Quản lý thông báo")
@Feature("Xóa thông báo")
public class XoaThongBao extends BaseTest {
    @BeforeMethod
    public void setUp(){
        setupDriver();
        driver.get("https://localhost:7011/");
    }

    @Test(description = "Verify successful deletion of user Admin-generated notifications",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra việc xóa thành công thông báo do tạo chính người dùng tạo ra")
    public void testScript7(String user, String pass) {
        try {
            Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
            login(user, pass);
            QuanLyThongBaoPage ql =new QuanLyThongBaoPage(driver);
            Allure.step("2. Điều hướng đến màn hình quản lý thông báo");
            ql.navigationQLTB();
            Allure.step("3. Kiểm tra thông báo đó có phải là của chính acc đó tạo không, nếu đúng thì click vào checkbox thông báo đó");
            ql.checkTBAccTao();
            Allure.step("4. Click vào icon xóa");
            ql.clickIconXoa();
            sleep(1000);
            Allure.step("5. Xác thực hiển thị alert cảnh báo có chắc chắn muốn xóa không");
            Assert.assertEquals(ql.alert(),"Bạn có chắc chắn muốn xóa 1 thông báo này?","Nội dung thông báo không khớp");
            sleep(500);
            Allure.step("6. Xác thực hiển thị alert thông báo thành công");
            Assert.assertEquals(ql.alert(),"Đã xóa 1 thông báo!","Nội dung thông báo không khớp");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test (description = "Verify that the administrator account user cannot delete notification created by others")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra người dùng tài khoản quản trị viên không thể xóa thông báo do người khác tạo")
    public void testScript8() throws IOException{
        try {
            loadJsonFile("src/test/resources/account.json");
            Allure.step("1. Đăng nhập vào hệ thống bằng acc admin");
            login(readJson("$.acc.admin[0].user"), readJson("$.acc.admin[0].pass"));
            QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
            Allure.step("2. Điều hướng đến màn hình quản lý thông báo");
            ql.navigationQLTB();
            Allure.step("3. Kiểm tra thông báo đó không phải là của chính acc Admin đó tạo không, nếu đúng thì click vào checkbox thông báo đó");
            ql.checkTBNotAccTao();
            Allure.step("4. Click vào icon xóa");
            ql.clickIconXoa();
            sleep(1000);
            Allure.step("5. Xác thực hiển thị alert với nội dung đúng theo tài liệu");
            Assert.assertEquals(ql.alert(), "Bạn không có quyền xóa 1 thông báo do người dùng khác đã tạo!", "Nội dung thông báo không khớp");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test (description = "Verify that admin users delete notifications created by themselves and others")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra người dùng quản trị chọn xóa cùng lúc nhiều thông báo do chính họ và người khác tạo")
    public void testScript9() throws IOException{
        try {
            loadJsonFile("src/test/resources/account.json");
            Allure.step("1. Đăng nhập vào hệ thống bằng acc admin");
            login(readJson("$.acc.admin[0].user"), readJson("$.acc.admin[0].pass"));
            QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
            Allure.step("2. Điều hướng đến màn hình quản lý thông báo");
            ql.navigationQLTB();
            Allure.step("3. Click vào những checkbox của thông báo do acc admin tạo và acc khác tạo");
            List<WebElement> listElements = driver.findElements(By.xpath("//div[@id='announce-detail']//a//small"));
            int total = listElements.size();
            int count_i = 1;
            int count_y = 1;
            for (int index = 1; index <= total; index++) {
                String from = ql.nguoiDang(index);
                if (!from.equals("From: Quản trị viên") && count_i < 4) {
                    ql.clickCheckbox(index);
                    count_i++;
                }else if(from.equals("From: Quản trị viên") && count_y < 3){
                    ql.clickCheckbox(index);
                    count_y++;
                }
            }
            Allure.step("4. Kiểm tra hiển thị số lượng đã chọn ở mục có đúng không");
            Assert.assertEquals(ql.soItem(),"5","Không hiển thị đúng số lượng đã chọn");
            Allure.step("5. Click vào icon xóa");
            ql.clickIconXoa();
            sleep(1000);
            Allure.step("6. Xác thực hiển thị alert hiển thị thông báo chỉ xóa được những thông báo do admin tạo");
            String ndAlert1 = "Bạn không có quyền xóa " + (count_i-1)+ " thông báo do người dùng khác đã tạo!\nBấm 'Ok' nếu bạn chắc chắn muốn xóa "+ (count_y-1) + " thông báo mà bạn đã tạo.";
            Assert.assertEquals(ql.alert(),ndAlert1,"Nội dung thông báo không khớp");
            Allure.step("7. Xác thực hiển thị alert thông báo thành công");
            sleep(500);
            String ndAlert2 = "Đã xóa "+(count_y-1)+" thông báo!";
            Assert.assertEquals(ql.alert(),ndAlert2,"Nội dung thông báo không khớp");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test (description = "Verify that a notification is displayed when the user clicks the delete icon without selecting any notification",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra thông báo được hiển thị khi người dùng nhấp vào biểu tượng xóa mà không chọn bất kỳ thông báo nào")
    public void testScript10(String user, String pass){
        try {
            Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
            login(user, pass);
            QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
            Allure.step("2. Điều hướng đến màn hình quản lý thông báo");
            ql.navigationQLTB();
            Allure.step("3. Click vào icon xóa");
            ql.clickIconXoa();
            sleep(1000);
            Allure.step("4. Xác thực hiển thị alert với nội dung phải chọn ít nhất môt cái để xóa");
            Assert.assertEquals(ql.alert(), "Vui lòng chọn ít nhất một thông báo để xóa.", "Nội dung thông báo không khớp");
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
