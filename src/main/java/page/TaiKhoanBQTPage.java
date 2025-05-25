package page;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;


public class TaiKhoanBQTPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor nhận driver
    public TaiKhoanBQTPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void navigateTKBQT(){
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='username']")));
        a.click();
        WebElement quanly = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dropdown-menu']/a[1]")));
        quanly.click();
        WebElement qltk = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='QA']")));
        qltk.click();
        WebElement tkbqt = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[@id='ul-small2']//a[1]")));
        tkbqt.click();
    }
    public void clickIconTao(){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='openPopupAccAdmin']")));
        icon.click();
    }
    public void enterCreateAcc(String tenht, String tendn, String vaitro, String pb, String sdt, String email, String trangthai, String mk, String nhaplaimk ){
        WebElement displayName = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='tenHienThi']")));
        displayName.sendKeys(tenht);
        WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='tenDangNhap']")));
        username.sendKeys(tendn);
        Select role = new Select(driver.findElement(By.xpath("//select[@id='vaiTro2']")));
        role.selectByVisibleText(vaitro);
        Select department = new Select(driver.findElement(By.xpath("//select[@id='phongBan2']")));
        department.selectByVisibleText(pb);
        WebElement phone = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='soDienThoai']")));
        phone.sendKeys(sdt);
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email']")));
        e.sendKeys(email);
        Select status = new Select(driver.findElement(By.xpath("//select[@id='trangThai2']")));
        status.selectByVisibleText(trangthai);
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='matKhau']")));
        password.sendKeys(mk);
        WebElement passwordagain = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='nhapLaiMatKhau']")));
        passwordagain.sendKeys(nhaplaimk);
    }
    public void clickBtnLuu(){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submitacc']")));
        button.click();
    }
    public void logout(){
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='username']")));
        a.click();
        WebElement dangXuat = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Đăng xuất')]")));
        dangXuat.click();
    }
    public boolean checkLoginSuccess(String tenht){
        try{
            WebElement check = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//b[contains(text(),'"+tenht+"')]")));
            return check.isDisplayed();
        }catch(Exception e){
            return false;
        }
    }
    public String alert(){
        Alert alert = driver.switchTo().alert(); // Switch qua alert
        String alertText = alert.getText(); // Lấy text của alert1
        alert.accept(); // Bấm OK alert đầu tiên
        return  alertText;
    }
    public boolean checkTBLoi(String tbloi){
        WebElement check = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+tbloi+"')]")));
        Boolean a = check.isDisplayed();
        return a;
    }

}
