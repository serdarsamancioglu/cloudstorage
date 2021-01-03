package com.udacity.jwdnd.course1.cloudstorage.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class SeleniumTest {

    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
        sleep();
        driver.findElement(By.id("signup-link")).click();
        sleep();
        driver.findElement(By.id("inputFirstName")).sendKeys("s");
        sleep();
        driver.findElement(By.id("inputLastName")).sendKeys("s");
        sleep();
        driver.findElement(By.id("inputUsername")).sendKeys("s");
        sleep();
        driver.findElement(By.id("inputPassword")).sendKeys("s");
        sleep();
        driver.findElement(By.tagName("button")).submit();
        sleep();
        driver.findElement(By.id("login-link")).click();
        sleep();
        driver.findElement(By.name("username")).sendKeys("s");
        sleep();
        driver.findElement(By.name("password")).sendKeys("s");
        sleep();
        driver.findElement(By.tagName("button")).submit();

        Thread.sleep(5000);
        driver.quit();
    }

    private static void sleep() throws InterruptedException {
        Thread.sleep(500);
    }
}
