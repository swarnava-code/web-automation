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
    private static final String FB_USER_NAME = new TesterCredentials().getUSERNAME(); //assign your fb username
    private static final String FB_PASSWORD = new TesterCredentials().getPASSWORD(); //assign your fb password
    static final boolean IS_HUMAN_PRESENCE = true;
    static final String BASE_URL = "https://www.snapdeal.com";
    static final String PRODUCT_NAME = "VIPPO VBH-658 BLUE FRENZY HEADPHONE"; //KnoX Sanitizers 600 mL Pack of 6  //VIPPO VBH-658 BLUE FRENZY HEADPHONE  //Dog Choke Chain Collar
    static String pincode = "700018";
    static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriverManager.chromedriver().driverVersion("97.0.4692.71").setup();
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        driver.get(BASE_URL);
        driver.manage().window().maximize();
        isThreadSleepRequired();
        driver.findElement(By.name("keyword")).sendKeys(PRODUCT_NAME, Keys.ENTER);
        isThreadSleepRequired();
        driver.findElement(By.xpath("//input[@placeholder='Enter your pincode']")).sendKeys(pincode);
        isThreadSleepRequired();
        driver.findElement(By.xpath("//button[@class='pincode-check']")).click();
        isThreadSleepRequired();
        for (int i = 0; i < 10; i++) {
            driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN); //mandatory scroll to find element
        }
        WebElement productAnchorTag = driver.findElement(By.partialLinkText(PRODUCT_NAME));
        String productLink = productAnchorTag.getAttribute("href");
        driver.get(productLink);
        isThreadSleepRequired();
        isScrollRequired();
        driver.findElement(By.id("add-cart-button-id")).click();
        isThreadSleepRequired();
        WebElement proceedToCheckout = driver.findElement(By.cssSelector("a[class='btn marR5']"));
        proceedToCheckout.click();
        isThreadSleepRequired();
        String snapdealWindow = driver.getWindowHandle();
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        WebElement loginFB = driver.findElement(By.id("fblogin"));
        loginFB.click();
        Set<String> driverWindowHandles = driver.getWindowHandles();
        for (String windowHandle : driverWindowHandles) {
            if(!driver.getWindowHandle().equals(windowHandle)){
                driver.switchTo().window(windowHandle);
                isThreadSleepRequired();
                driver.findElement(By.id("email")).sendKeys(FB_USER_NAME);
                isThreadSleepRequired();
                driver.findElement(By.id("pass")).sendKeys(FB_PASSWORD, Keys.ENTER);
                isThreadSleepRequired();
                break;
            }
        }
        driver.switchTo().window(snapdealWindow);
        isThreadSleepRequired();
        WebElement makePayment = webDriverWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("make-payment"))
        );
        if(makePayment.isDisplayed()) {
            isThreadSleepRequired();
            isScrollRequired();
            isThreadSleepRequired();
            driver.findElement(By.id("make-payment")).click();
            isThreadSleepRequired();
            isScrollRequired();
        }
        isThreadSleepRequired();
        driver.close();
        driver.quit();
    }

    static void isScrollRequired() {
        if(IS_HUMAN_PRESENCE){
            for (int i = 0; i < 10; i++) {
                driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);//
            }
        }
    }

    static void isThreadSleepRequired() throws InterruptedException {
        if(IS_HUMAN_PRESENCE){
            Thread.sleep(2000);
        }
    }
}