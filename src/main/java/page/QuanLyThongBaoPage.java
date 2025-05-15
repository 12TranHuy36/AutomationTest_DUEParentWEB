package page;

import common.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class QuanLyThongBaoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor nhận driver
    public QuanLyThongBaoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void navigationTB(){
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='username']")));
        a.click();
        WebElement quanly = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dropdown-menu']/a[1]")));
        quanly.click();
        WebElement quanlyTB = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'/Admin/GetAnnounces')]")));
        quanlyTB.click();
    }
    public void navigationQLTB(){
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='username']")));
        a.click();
        WebElement quanly = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='dropdown-menu']/a[1]")));
        quanly.click();
        WebElement quanlyTB = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href,'/Admin/GetAnnounces')]")));
        quanlyTB.click();
        WebElement iconSetting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='all-icon']//a[3]")));
        iconSetting.click();
    }
    public void clickIconThem(){
        WebElement iconThem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='openPopup']")));
        iconThem.click();
    }
    public void enterTieuDe(String td){
        WebElement tieuDe = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='tieuDe']")));
        tieuDe.clear();
        tieuDe.sendKeys(td);
    }
    public void enterNoidung(String nd){
        WebElement noiDung = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='editor']//p")));
        noiDung.clear();
        noiDung.sendKeys(nd);
    }
    public void clickButtonLuu(){
        WebElement buttonLuu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='submitThongBao']")));
        buttonLuu.click();
    }
    public void clickAndInsertLink(String ln){
        WebElement insertLinkBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='ql-link']")));
        insertLinkBtn.click();
        WebElement linkInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ql-tooltip ql-editing']//input")));
        linkInput.sendKeys(ln);
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='ql-tooltip ql-editing']//a[2]")));
        saveBtn.click();
    }
    public void clickTBmoi(){
        WebElement TBmoi = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='announce-detail'][1]//a")));
        TBmoi.click();
    }
    public void clickCheckbox(int index){
        String xpathCheckbox = "//div[@id='announce-detail'][" + index + "]//input[@class='delete-checkbox']";
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathCheckbox)));
        checkbox.click();
    }
    public void clickIconXoa(){
        WebElement iconXoa = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//img[@class='icon delete-selected']")));
        iconXoa.click();
    }
    public String soItem(){
        String item = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@id='selected-count']"))).getText().replaceAll("[^0-9]", "");
        return item;
    }
    public String alert(){
        Alert alert = driver.switchTo().alert(); // Switch qua alert
        String alertText = alert.getText(); // Lấy text của alert1
        alert.accept(); // Bấm OK alert đầu tiên
        return  alertText;
    }
    public String nguoiDang(int index){
        WebElement i = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='announce-detail'][" + index + "]//a//small")));
        String from = i.getText().trim();
        return from;
    }
    public void logout(){
        WebElement a = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='username']")));
        a.click();
        WebElement dangXuat = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Đăng xuất')]")));
        dangXuat.click();
    }
    public Boolean searchTBNew(String title){
        try {
            String xpath = "((//div[@id='announce-detail'])[1]//a[contains(text(),'" + title + "')])[1]";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return true; // Nếu tìm thấy thì return true
        } catch (TimeoutException e) {
            return false; // Nếu không tìm thấy (bị timeout) thì return false
        }
    }
    public void clickIcon3Cham(String status){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='dropdown']")));
        icon.click();
        WebElement trangThai = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'"+status+"')]")));
        trangThai.click();
    }
    public void clickIconBack(){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div/span[@class='title']/preceding-sibling::a")));
        icon.click();
    }
    public void clickIconCaiDat(){
        WebElement iconSetting = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='all-icon']//a[3]")));
        iconSetting.click();
    }
    public boolean checkStatusTB(int index){
        WebElement status = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@id='announce-date'])["+index+"]")));
        BaseTest b = new BaseTest();
        String mau = b.rgbaToHex(status.getCssValue("background-color"));
        if(mau.equals("#1261a6")){
            return true;//Cong khai
        }else{
            return false;//An
        }
    }
    public String checkStatusTBCT(){
        String status = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[contains(text(),'Trạng thái')]"))).getText();
        if(status.contains("Công khai")){
            return "Công khai";
        }else{
            return "Ẩn";
        }
    }
    public void clickXemTBCT(int index){
        WebElement tb = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@id='announce-detail'])["+index+"]//a")));
        tb.click();
    }
    public void clickIconStatus(){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='all-icon']//a[@style='text-decoration:none']")));
        icon.click();
    }
    public void clickIconEdit(){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='edit-btn']")));
        icon.click();
    }
    public boolean checkTdNdEdit(String td, String nd){
        String title = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b//p[@class='announces']"))).getText().trim();
        String content = wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("(//div//preceding-sibling::p)[2]")))).getText().trim();
        if(td.contains(title) && nd.contains(content)){
            return true;
        }else{
            return false;
        }
    }
    public boolean checkDisplayNoBlank(){
        String tbTd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='errorTD']"))).getText().trim();
        String tbNd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='errorNoiDng']"))).getText().trim();
        if(tbTd.equals("Không được để trống!") && tbNd.equals("Không được để trống!")){
            return true;
        }else{
            return false;
        }
    }
    public String checkTDTB(){
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@href, '/Home/DetailAnnounce')]")));
        String fullText = element.getText();
        String mainText = fullText.split("From:")[0].trim();  // Loại bỏ phần từ "From:" trở đi
    return mainText;
    }
    public void clickIconLocQLTB(){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='openPopupFilter2']")));
        icon.click();
    }
    public void clickIconLocTB(){
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='openPopupFilter']")));
        icon.click();
    }
    public void filterByStatus(String value) {
        WebElement dropdownTrangThai = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='xmas-popup-filter-2']//select[@id='trangThai']")));
        Select select = new Select(dropdownTrangThai);
        select.selectByValue(value); // "1" = Công khai, "0" = Ẩn, "" = Tất cả
        // Click nút "Lọc"
        WebElement buttonLoc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='xmas-popup-filter-2']//button[@class='apply']")));
        buttonLoc.click();
    }
    public void filterByPersonCreate(String value) {
        WebElement dropdownNguoiDang = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='xmas-popup-filter']//select[@id='nguoiTao']")));
        Select select = new Select(dropdownNguoiDang);
        select.selectByValue(value); // "1" = Công khai, "0" = Ẩn, "" = Tất cả
        // Click nút "Lọc"
        WebElement buttonLoc = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='xmas-popup-filter']//button[@class='apply']")));
        buttonLoc.click();
    }


}
