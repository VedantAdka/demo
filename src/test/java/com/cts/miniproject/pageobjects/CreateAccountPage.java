package com.cts.miniproject.pageobjects;

import com.cts.miniproject.frameworkutils.CommonUtils;
import com.cts.miniproject.seleniumutils.ActionUtil;
import com.cts.miniproject.seleniumutils.JavaScriptUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateAccountPage extends BasePage {
    @FindBy(xpath = "//div/h2")
    private WebElement logo;
    @FindBy(tagName = "a")
    private List<WebElement> links;
    @FindBy(xpath = "//p[@class='tnc']/a[1]")
    private WebElement termsAndConditionLink;

    public CreateAccountPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean logoIsDisplayed() {
        return logo.isDisplayed();
    }

    public int noOfLinks() {
        return links.size();
    }

    public List<String> Links() {
        List<String> linksInPage = new ArrayList<>();
        for (WebElement e : links) {
            if (e.getText().equals("")) {
                linksInPage.add("link1");
            } else {
                linksInPage.add(e.getText());
            }
        }
        return linksInPage;
    }

    public void clickOnTermsAndConditionsLink() {
        JavaScriptUtil.JSscrollToElement(termsAndConditionLink,driver);
        CommonUtils.sureWait(2);
        ActionUtil.moveToElementAction(driver,termsAndConditionLink);
        JavaScriptUtil.JSclick(termsAndConditionLink,driver);
    }

}
