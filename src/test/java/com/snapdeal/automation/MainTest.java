package com.snapdeal.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

class MainTest {
    private static String FB_USER_NAME = new TesterCredentials().getUSERNAME(); //assign your fb username
    private static String FB_PASSWORD = new TesterCredentials().getPASSWORD(); //assign your fb password
    static WebDriver driver;
    public static void main(String[] args) throws InterruptedException {
        String baseUrl = "https://www.snapdeal.com";
        String productName = "KnoX Sanitizers 600 mL Pack of 6";
        String pincode = "700018";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
        WebDriverManager.chromedriver().driverVersion("97.0.4692.71").setup();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        driver.get(baseUrl);
        driver.manage().window().maximize();
        callingThreadOrNot();
        driver.findElement(By.name("keyword")).sendKeys(productName, Keys.ENTER);
        callingThreadOrNot();
        driver.findElement(By.xpath("//input[@placeholder='Enter your pincode']")).sendKeys(pincode);
        callingThreadOrNot();
        driver.findElement(By.xpath("//button[@class='pincode-check']")).click();
        callingThreadOrNot();
        for (int i = 0; i < 10; i++) {
            //mandatory to find element
            driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
        }
        WebElement productAnchorTag = driver.findElement(By.partialLinkText(productName));
        String productLink = productAnchorTag.getAttribute("href");
        driver.get(productLink);
        callingThreadOrNot();
        for (int i = 0; i < 10; i++) {
            driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
        }
        driver.findElement(By.id("add-cart-button-id")).click();
        callingThreadOrNot();
        driver.findElement(By.cssSelector("a[class='btn marR5']")).click();
        callingThreadOrNot();
        String snapdealWindow = driver.getWindowHandle();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        WebElement loginFB = webDriverWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("fblogin"))
        );
        if(loginFB.isDisplayed()){
            loginFB.click();
            callingThreadOrNot();
        }
        Set<String> driverWindowHandles = driver.getWindowHandles();
        for (String windowHandle : driverWindowHandles) {
            if(!driver.getWindowHandle().equals(windowHandle)){
                driver.switchTo().window(windowHandle);
                callingThreadOrNot();
                driver.findElement(By.id("email")).sendKeys(FB_USER_NAME);
                callingThreadOrNot();
                driver.findElement(By.id("pass")).sendKeys(FB_PASSWORD, Keys.ENTER);
                callingThreadOrNot();
                break;
            }
        }
        driver.switchTo().window(snapdealWindow);
        callingThreadOrNot();
        WebElement makePayment = webDriverWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("make-payment"))
        );
        if(makePayment.isDisplayed()){
            callingThreadOrNot();
            for (int i = 0; i < 20; i++) {
                driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
            }
            callingThreadOrNot();
            driver.findElement(By.id("make-payment")).click();
            callingThreadOrNot();
            for (int i = 0; i < 10; i++) {
                driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
            }
        }
        callingThreadOrNot();
        driver.close();
        driver.quit();
    }
    static void callingThreadOrNot() throws InterruptedException {
        boolean isHumanPresence = false;
        if(isHumanPresence){
            Thread.sleep(2000);
        }
    }
}