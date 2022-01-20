package com.snapdeal.automation;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

class MainTest {
    public static void main(String[] args) throws InterruptedException {
        String baseUrl = "https://www.snapdeal.com";
        WebDriverManager.chromedriver().driverVersion("97.0.4692.71").setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
        driver.manage().timeouts().scriptTimeout(Duration.ofMinutes(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
        driver.get(baseUrl);
        String originalWindow = driver.getWindowHandle();
        System.out.println(driver.getCurrentUrl());
        Thread.sleep(3000);
        driver.close();
        driver.quit();
    }
}