package com.cts.miniproject.pageobjects;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

abstract public class BasePage {
     protected WebDriver driver;

     protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getPageTitle() {
         return driver.getTitle();
    }

    public List<String> getWindowId() {
        List<String> winId = new ArrayList<>(driver.getWindowHandles());
        return winId;
    }

    public void maximizePage() {
        driver.manage().window().maximize();
    }

    public void switchTabs(String id) {
         driver.switchTo().window(id);
    }

    public void closePage() {
         driver.close();
    }
}
