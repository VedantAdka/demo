package com.cts.miniproject.pageobjects;

import java.util.ArrayList;
import java.util.List;

import com.cts.miniproject.seleniumutils.ActionUtil;
import com.cts.miniproject.seleniumutils.Waits;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {
    @FindBy(xpath = "//div[@class='toprightlinks']/p[2]/a[2]")
    private WebElement createAccBtn;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickOnCreateAnAccountButton() {
        ActionUtil.moveToElementAction(driver, createAccBtn);
        Waits.waitElementToBeClickable(driver, createAccBtn, 30).click();
    }

}
