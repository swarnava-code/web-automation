package com.snapdeal.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Set;

class MainTest {
    private static String FB_USER_NAME = "Enter your username";
    private static String FB_PASSWORD = "Enter your password";
    public static void main(String[] args) throws InterruptedException, AWTException {
        String baseUrl = "https://www.snapdeal.com";
        String productName = "KnoX Sanitizers 600 mL Pack of 6";
        String pincode = "700018";
        WebDriverManager.chromedriver().driverVersion("97.0.4692.71").setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        driver.get(baseUrl);
        System.out.println(driver.getCurrentUrl());
        driver.manage().window().maximize();
        driver.findElement(By.name("keyword")).sendKeys(productName, Keys.ENTER);
        driver.findElement(By.xpath("//input[@placeholder='Enter your pincode']")).sendKeys(pincode);
        driver.findElement(By.xpath("//button[@class='pincode-check']")).click();
        //Thread.sleep(3000);
        for (int i = 0; i < 10; i++) {
            driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
        }
        //Thread.sleep(3000);
        WebElement productAnchorTag = driver.findElement(By.partialLinkText(productName));
        String productLink = productAnchorTag.getAttribute("href");
        System.out.println(productLink);
        driver.get(productLink);
        //Thread.sleep(3000);
        for (int i = 0; i < 10; i++) {
            driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
        }
        driver.findElement(By.id("add-cart-button-id")).click();
        //Thread.sleep(5000);
        driver.findElement(By.cssSelector("a[class='btn marR5']")).click();
        String snapdealWindow = driver.getWindowHandle();
        driver.findElement(By.id("fblogin")).click();
        //Thread.sleep(4000);
        Set<String> driverWindowHandles = driver.getWindowHandles();
        for (String windowHandle : driverWindowHandles) {
            if(!driver.getWindowHandle().equals(windowHandle)){
                driver.switchTo().window(windowHandle);
                driver.findElement(By.id("email")).sendKeys(FB_USER_NAME);
                driver.findElement(By.id("pass")).sendKeys(FB_PASSWORD, Keys.ENTER);
                break;
            }
        }
        driver.switchTo().window(snapdealWindow);
        Thread.sleep(10000);
        for (int i = 0; i < 30; i++) {
            driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
        }
        driver.findElement(By.id("make-payment")).click();
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            driver.findElement(By.xpath("//body")).sendKeys(Keys.ARROW_DOWN);
        }
    }
}