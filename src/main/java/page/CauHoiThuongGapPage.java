package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class CauHoiThuongGapPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor nhận driver
    public CauHoiThuongGapPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void navigateCHTG(){
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='username']")));
        a.click();
        WebElement quanly = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dropdown-menu']/a[1]")));
        quanly.click();
        WebElement hoiDap = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='RL-NK']")));
        hoiDap.click();
        WebElement CHTG = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(., 'Câu hỏi thường gặp')]")));
        CHTG.click();
    }
    public void clickIconThem(){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='openPopupCauHoi']")));
        icon.click();
    }
    public void clickButtonLuu(){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submitCauHoi']")));
        button.click();
    }
    public boolean checkDisplayNoBlank(){
        String tbCd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='errorChuDe']"))).getText().trim();
        String tbCh = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='errorNoiDung']"))).getText().trim();
        String tbCtl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='errorCauTraLoi']"))).getText().trim();
        if(tbCd.equals("Không được để trống!") && tbCh.equals("Không được để trống!") && tbCtl.equals("Không được để trống!")){
            return true;
        }else{
            return false;
        }
    }

    public void addChuDeAndChonNo(String cd){
        WebElement chuDe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mb-3']//select[@id='chuDe']")));
        chuDe.click();
        WebElement themMoiCD = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[@value='__them_moi__']")));
        themMoiCD.click();
        WebElement chuDeMoi = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='chuDe1']")));
        chuDeMoi.sendKeys(cd);
        WebElement clickpb = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='phongBan1']")));
        clickpb.click();
        List<WebElement> listElements = driver.findElements(By.xpath("//select[@id='phongBan1']//option"));
        int total = listElements.size();
        if(total==1){
            WebElement phongBan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='phongBan1']//option[2]")));
            phongBan.click();
        }else {
            Random random = new Random();
            int randomNumber = random.nextInt(total-1) + 2;
            WebElement phongBan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='phongBan1']//option["+randomNumber+"]")));
            phongBan.click();
        }
        WebElement clickTt = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='trangThai7']")));
        clickTt.click();
        WebElement trangThai = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='trangThai7']//option[contains(text(),'Mở')]")));
        trangThai.click();
        WebElement buttonLuu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submitChuDe']")));
        buttonLuu.click();
        WebElement chuDelai = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mb-3']//select[@id='chuDe']")));
        chuDelai.click();
        WebElement chonCDMoiThem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='mb-3']//select[@id='chuDe']//option[contains(text(),'"+cd+"')]")));
        chonCDMoiThem.click();
    }
    public void enterCauHoi(String cauHoi){
        WebElement ch = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='noiDung']")));
        ch.sendKeys(cauHoi);
    }
    public void enterCauTraLoi(String cauTraLoi){
        WebElement ctl =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='cauTraLoi']")));
        ctl.sendKeys(cauTraLoi);
    }
    public void Search(String tuKhoa){
        WebElement tk = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='keyword']")));
        tk.sendKeys(tuKhoa);
        WebElement btnTK = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='search-Q']//button")));
        btnTK.click();
    }
    public boolean checkSearchResult(String tuKhoa){
        List<WebElement> listElements = driver.findElements(By.xpath("//h3[@class='question']"));
        int total = listElements.size();
        boolean check = false;
        if(total != 0){
            int count = 0;
            for (int i = 1; i <= total ; i++) {
                String xpath = "(//h3[@class='question'])["+i+"]";
                String ch = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).getText().trim();
                if(ch.contains(tuKhoa)){
                    count++;
                }
            }
            if(count == total){
                check = true;
            }
            return check;
        }else{
            return check;
        }


    }
}
