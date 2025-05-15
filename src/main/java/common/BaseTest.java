package common;

import com.jayway.jsonpath.JsonPath;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Random;

public class BaseTest {
    // Khai báo đối tượng WebDriver để điều khiển trình duyệt
    protected WebDriver driver;
    // Khai báo đối tượng WebDriverWait để chờ đợi các phần tử trên trang
    protected WebDriverWait wait;
    protected String jsonContent;


    // Hàm setup WebDriver
    public void setupDriver() {
        // Cài đặt WebDriver cho Chrome
        WebDriverManager.chromedriver().setup();
        // Thiết lập kích thước cửa sổ trình duyệt
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("force-device-scale-factor=1");
        options.addArguments("--guest");
        // Khởi tạo đối tượng ChromeDriver với các tùy chọn đã thiết lập
        driver = new ChromeDriver(options);
        // Khởi tạo WebDriverWait với thời gian chờ là 10 giây
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Hàm đọc file JSON
    public void loadJsonFile(String filePath) throws IOException {
        jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
    }

    // Hàm lấy dữ liệu trong JSON theo path
    public String readJson(String path) {
        return JsonPath.read(jsonContent, path);
    }

    // Hàm login chung
    public void login(String username, String password) {
        WebElement userNameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("phone")));
        userNameInput.sendKeys(username);

        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        passwordInput.sendKeys(password);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
        loginButton.click();
    }

    // Hàm ngủ tạm thời (nếu cần)
    public void sleep(int time){
        try{
            Thread.sleep(time);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    // Hàm chuyển đổi từ rgba sang HEX
    public String rgbaToHex(String color) {
        String[] numbers = color.replace("rgba(", "").replace(")", "").split(",");
        int r = Integer.parseInt(numbers[0].trim());
        int g = Integer.parseInt(numbers[1].trim());
        int b = Integer.parseInt(numbers[2].trim());
        return String.format("#%02x%02x%02x", r, g, b);
    }
    // Hàm tạo chuỗi ký tự bất kỳ
    public String taoChuoiKyTu(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }
    @Attachment(value = "Screenshot on failure", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
