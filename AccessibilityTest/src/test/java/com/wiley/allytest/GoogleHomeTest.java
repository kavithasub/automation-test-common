package com.wiley.allytest;

import com.deque.axe.AXE;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;

public class GoogleHomeTest {

    private static final URL scriptUrl = GoogleHomeTest.class.getResource("/axe.min.js");
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.get("https://www.google.com");
    }

    @Test
    public void googleHomeTest() {
        JSONObject response;
        response = new AXE.Builder(driver, scriptUrl).analyze();
        JSONArray violations = response.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("NO ISSUES");
        } else {
            AXE.writeResults("googleHomeTest", response);
//            Assert.assertTrue(false,AXE.report(violations));
            Assert.fail(AXE.report(violations));

        }

    }

    @Test
    public void testUsedWebElement() {
        JSONObject response;
        response = new AXE.Builder(driver, scriptUrl).analyze(driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/img")));
        JSONArray violations = response.getJSONArray("violations");

        if (violations.length() == 0) {
            System.out.println("No Violations Found in Web Element");
        } else {
            AXE.writeResults("testUsedWebElement", response);
            Assert.fail(AXE.report(violations));
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
