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
import page.TaiKhoanBQTPage;

public class TaoTaiKhoan extends BaseTest {
    @BeforeMethod
    public void setUp(){
        setupDriver();
        driver.get("https://localhost:7011/");
    }

    @Test(description = "Verify that the administrator has successfully created a new account and the user has used the newly created account to successfully log into the system.",
            dataProvider = "AccountAdminAndAccValid", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra người quản trị đã tạo thành công tài khoản mới và người dùng đã sử dụng tài khoản mới tạo để đăng nhập vào hệ thống thành công.")
    public void testScript21(String user, String pass, String tenht, String tendn, String vaitro, String pb, String sdt, String email, String trangthai, String mk, String nhaplaimk ){
        String tendangnhap = tendn+taoChuoiKyTu(5);
        String tenhienthi = tenht+taoChuoiKyTu(5);
        try{
            Allure.step("1. Đăng nhập vào hệ thống bằng acc Admin");
            login(user, pass);
            TaiKhoanBQTPage tk = new TaiKhoanBQTPage(driver);
            Allure.step("2. Điều hướng đến màn hình tài khoản ban quản trị");
            tk.navigateTKBQT();
            Allure.step("4. Click icon Tạo tài khoản");
            tk.clickIconTao();
            Allure.step("5. Nhập thông tin vào form tạo tài khoản");
            tk.enterCreateAcc(tenhienthi, tendangnhap, vaitro, pb, sdt, email, trangthai, mk, nhaplaimk );
            Allure.step("6. Nhấn Lưu");
            tk.clickBtnLuu();
            Allure.step("7. Đăng xuất acc admin");
            tk.logout();
            Allure.step("8. Kiểm tra đăng nhập bằng acc mới tạo");
            login(tendangnhap, mk);
            Assert.assertTrue(tk.checkLoginSuccess(tenhienthi), "Đăng nhập thất bại: Không tìm thấy tên acc hiển thị");

        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e; // Rất quan trọng để test bị fail
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e); // Ném lại để TestNG biết có lỗi
        }
    }

    @Test(description = "Verify that the alert is displayed when Admin creates an account with a login name that matches the previous login name.",
            dataProvider = "AccountAdminAndAccValid", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra cảnh báo được hiển thị khi Quản trị viên tạo tài khoản có tên đăng nhập trùng với tên đăng nhập trước đó.")
    public void testScript22(String user, String pass, String tenht, String tendn, String vaitro, String pb, String sdt, String email, String trangthai, String mk, String nhaplaimk ){
        String tendangnhap = tendn+taoChuoiKyTu(5);
        String tenhienthi1 = tenht+taoChuoiKyTu(5);
        String tenhienthi2 = tenht+taoChuoiKyTu(5);
        try{
            Allure.step("1. Đăng nhập vào hệ thống bằng acc Admin");
            login(user, pass);
            TaiKhoanBQTPage tk = new TaiKhoanBQTPage(driver);
            Allure.step("2. Điều hướng đến màn hình tài khoản ban quản trị");
            tk.navigateTKBQT();
            Allure.step("4. Click icon Tạo tài khoản");
            tk.clickIconTao();
            Allure.step("5. Nhập thông tin vào form tạo tài khoản");
            tk.enterCreateAcc(tenhienthi1, tendangnhap, vaitro, pb, sdt, email, trangthai, mk, nhaplaimk );
            Allure.step("6. Nhấn Lưu");
            tk.clickBtnLuu();
            sleep(1000);
            Allure.step("7. Click icon Tạo tài khoản");
            tk.clickIconTao();
            Allure.step("8. Nhập thông tin vào form tạo tài khoản với tên đăng nhập trùng");
            tk.enterCreateAcc(tenhienthi2, tendangnhap, vaitro, pb, sdt, email, trangthai, mk, nhaplaimk );
            Allure.step("9. Nhấn Lưu");
            tk.clickBtnLuu();
            sleep(1000);
            Allure.step("6. Xác thực hiển thị alert hiển thị thông báo tên đăng nhập đã được sử dụng");
            String ndAlert = "Tên đăng nhập này đã được sử dụng. Vui lòng thử lại!";
            Assert.assertEquals(tk.alert(),ndAlert,"Nội dung thông báo không khớp");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e; // Rất quan trọng để test bị fail
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e); // Ném lại để TestNG biết có lỗi
        }
    }

    @Test(description = "Verify that a warning is displayed when an Administrator creates an account with a display name that matches a previous display name.",
            dataProvider = "AccountAdminAndAccValid", dataProviderClass = ReadDataProvider.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Kiểm tra cảnh báo được hiển thị khi Quản trị viên tạo tài khoản có tên hiển thị trùng với tên hiển thị trước đó.")
    public void testScript23(String user, String pass, String tenht, String tendn, String vaitro, String pb, String sdt, String email, String trangthai, String mk, String nhaplaimk ){
        String tendangnhap1 = tendn+taoChuoiKyTu(5);
        String tendangnhap2 = tendn+taoChuoiKyTu(5);
        String tenhienthi = tenht+taoChuoiKyTu(5);

        try{
            Allure.step("1. Đăng nhập vào hệ thống bằng acc Admin");
            login(user, pass);
            TaiKhoanBQTPage tk = new TaiKhoanBQTPage(driver);
            Allure.step("2. Điều hướng đến màn hình tài khoản ban quản trị");
            tk.navigateTKBQT();
            Allure.step("4. Click icon Tạo tài khoản");
            tk.clickIconTao();
            Allure.step("5. Nhập thông tin vào form tạo tài khoản");
            tk.enterCreateAcc(tenhienthi, tendangnhap1, vaitro, pb, sdt, email, trangthai, mk, nhaplaimk );
            Allure.step("6. Nhấn Lưu");
            tk.clickBtnLuu();
            sleep(1000);
            Allure.step("7. Click icon Tạo tài khoản");
            tk.clickIconTao();
            Allure.step("8. Nhập thông tin vào form tạo tài khoản với tên hiển thị trùng");
            tk.enterCreateAcc(tenhienthi, tendangnhap2, vaitro, pb, sdt, email, trangthai, mk, nhaplaimk );
            Allure.step("9. Nhấn Lưu");
            tk.clickBtnLuu();
            sleep(1000);
            Allure.step("6. Xác thực hiển thị alert hiển thị thông báo tên hiển thị đã được sử dụng");
            String ndAlert = "Tên hiển thị này đã được sử dụng. Vui lòng thử lại!";
            Assert.assertEquals(tk.alert(),ndAlert,"Nội dung thông báo không khớp");
        } catch (AssertionError e) {
            Allure.step("Kiểm tra thất bại: " + e.getMessage());
            throw e; // Rất quan trọng để test bị fail
        } catch (Exception e) {
            Allure.step("Lỗi xảy ra trong quá trình chạy test: " + e.getMessage());
            throw new RuntimeException(e); // Ném lại để TestNG biết có lỗi
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
