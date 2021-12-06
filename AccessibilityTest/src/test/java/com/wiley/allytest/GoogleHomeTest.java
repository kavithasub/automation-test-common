package com.wiley.allytest;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;

public class GoogleHomeTest {

    WebDriver driver;
    private static final URL scriptUrl = GoogleHomeTest.class.getResource("/axe.min.js");

    @BeforeMethod
    public void setup(){
        WebDriverManager.chromedriver().setup();

//        WebDriver driver = new RemoteWebDriver("http://localhost:9515", DesiredCapabilities.chrome());
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
    }

    @Test
    public void googleHomeTest(){
        JSONObject response = new AXE.Builder(driver, scriptUrl).analyze();
        JSONArray violations = response.getJSONArray("violations");

        if(violations.length()==0){
            System.out.println("NO ISSUES");
        }else{
            AXE.writeResults("googleHomeTest",response);
            Assert.assertTrue(false,AXE.report(violations));

        }

    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}
