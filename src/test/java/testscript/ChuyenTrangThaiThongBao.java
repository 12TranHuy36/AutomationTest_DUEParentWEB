package testscript;

import common.BaseTest;

import data.ReadDataProvider;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page.QuanLyThongBaoPage;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ChuyenTrangThaiThongBao extends BaseTest {
    @BeforeMethod
    public void setUp(){
        setupDriver();
        driver.get("https://localhost:7011/");
    }
    @Test(description = "Verify that admin can hide a newly added notification and ensure it is no longer visible in the notification list",
            dataProvider = "AccountData", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra quản trị viên có thể ẩn thông báo mới được thêm vào và đảm bảo thông báo đó không còn hiển thị trong danh sách thông báo nữa")
    public void testScript11(String user, String pass) throws IOException {
        Allure.step("1. Đăng nhập vào hệ thống bằng acc "+user);
        login(user,pass);
        loadJsonFile("src/test/resources/account.json");
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        Allure.step("2. Điều hướng đến màn hình thông báo");
        ql.navigationTB();
        Allure.step("3. Thêm một thông báo mới");
        ql.clickIconThem();
        ql.enterTieuDe("Check status change "+user);
        ql.enterNoidung("Check status change - Tester");
        ql.clickButtonLuu();
        Allure.step("5. Kiểm tra thông báo mới được thêm hiển thị ở danh sách màn hình thông báo");
        Assert.assertTrue(ql.searchTBNew("Check status change "+user), "Không tìm thấy thông báo mới thêm trong danh sách");

        if(!user.equals(readJson("$.acc.admin[0].user"))){
            Allure.step("6. Nếu không phải acc Admn thì Đăng xuất không thì chuyển tới Bước 9");
            ql.logout();
            Allure.step("7. Đăng nhập vào acc Admin để có quyền chuyển đổi trạng thái thông báo");
            login(readJson("$.acc.admin[0].user"), readJson("$.acc.admin[0].pass"));
            Allure.step("8. Điều hướng tới màn hình quản lý thông báo");
            ql.navigationQLTB();
        }else{
            Allure.step("9. Điều hướng tới màn hình quản lý thông báo");
            ql.clickIconCaiDat();
        }
        try {
            Allure.step("10. Kiểm tra định dạng màu thông báo đó ở định dạng công khai không");
            sleep(1000);
            Assert.assertTrue(ql.checkStatusTB(1), "Thông báo không ở định dạng màu trạng thái công khai");
            Allure.step("11. Tìm thông báo mới vừa thêm và click vào checkbox của thông báo đó");
            ql.clickCheckbox(1);
            Allure.step("12. Chọn icon 3 chấm click trạng thái ẩn");
            ql.clickIcon3Cham("Ẩn");
            sleep(500);
            Allure.step("13. Quay về kiểm tra thông báo ẩn đó có hiển thị ở danh sách màn hình thông báo không");
            ql.clickIconBack();
            sleep(3000);
            Assert.assertFalse(ql.searchTBNew("Check status change "+user),"Thông báo đã ẩn nhưng vẫn xuất hiển ở danh sánh màn hình Thông báo");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify that the admin changes the hidden notification to public status")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra quản trị viên thay đổi thông báo ẩn thành trạng thái công khai thành công")
    public void testScript12() throws IOException {
        loadJsonFile("src/test/resources/account.json");
        login(readJson("$.acc.admin[0].user"), readJson("$.acc.admin[0].pass"));
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        Allure.step("1. Điều hướng đến màn hình quản lý thông báo");
        ql.navigationQLTB();
        Allure.step("2. Duyệt danh sách thông báo kiểm tra trạng thái thông báo");
        List<WebElement> listElements = driver.findElements(By.xpath("//div[@id='announce-date']"));
        int total = listElements.size();
        boolean status2 = false;
        for (int index = 1; index <= total; index++) {
               boolean status1 = ql.checkStatusTB(index);
            if (!status1) {
                Allure.step("3. Cái nào ở trạng thái ẩn thì click check box");
                ql.clickCheckbox(index);
                Allure.step("4. Chuyển dổi sang trạng thái công khai");
                ql.clickIcon3Cham("Công khai");
                sleep(1000);
                status2 = ql.checkStatusTB(index);
                break;
            }
        }
        //
        try {
            Allure.step("5. Xác thực trạng thái của thông báo vừa mới chuyển sang trạng thái công khai");
            Assert.assertTrue(status2, "Thông báo vẫn ở trạng thái ẩn mặc dù đã ấn chuyển trạng thái từ ẩn sang công khai");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Test(description = "Verify that the notification status is updated when the Admin changes the notification status from public to hidden and vice versa from the notification details screen")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra trạng thái thông báo được cập nhật khi Quản trị viên thay đổi trạng thái thông báo từ công khai sang ẩn và ngược lại từ màn hình chi tiết thông báo")
    public void testScript13() throws IOException {
        loadJsonFile("src/test/resources/account.json");
        login(readJson("$.acc.admin[0].user"), readJson("$.acc.admin[0].pass"));
        QuanLyThongBaoPage ql = new QuanLyThongBaoPage(driver);
        Allure.step("1. Điều hướng đến màn hình quản lý thông báo");
        ql.navigationQLTB();
        Allure.step("2. Click vào một thông báo bất kỳ");
        List<WebElement> listElements = driver.findElements(By.xpath("//div[@id='announce-date']"));
        int total = listElements.size();
        Random random = new Random();
        int randomNumber = random.nextInt(total) + 1;
        Allure.step("3. Click vào xem chi ti thông báo đó");
        sleep(1000);
        ql.clickXemTBCT(randomNumber);
        Allure.step("4. Kiểm tra trạng thái thông báo ban đầu");
        String statusTruoc = ql.checkStatusTBCT();
        try{
            if (statusTruoc.equals("Công khai")){
                Allure.step("5. Click vào icon chuyển đổi trạng thái và kiểm tra");
                ql.clickIconStatus();
                sleep(1000);
                Assert.assertEquals(ql.checkStatusTBCT(),"Ẩn","Đã click chuyển đổi trạng thái mà không cập nhật");
                Allure.step("6. Click vào icon chuyển đổi trạng thái và check lại");
                ql.clickIconStatus();
                sleep(1000);
                Assert.assertEquals(ql.checkStatusTBCT(),"Công khai","Đã click chuyển đổi trạng thái mà không cập nhật");
            }else{
                Allure.step("5. Click vào icon chuyển đổi trạng thái và kiểm tra");
                ql.clickIconStatus();
                sleep(1000);
                Assert.assertEquals(ql.checkStatusTBCT(),"Công khai","Đã click chuyển đổi trạng thái mà không cập nhật");
                Allure.step("6. Click vào icon chuyển đổi trạng thái và check lại");
                ql.clickIconStatus();
                sleep(1000);

                Assert.assertEquals(ql.checkStatusTBCT(),"Ẩn","Đã click chuyển đổi trạng thái mà không cập nhật");
            }
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
