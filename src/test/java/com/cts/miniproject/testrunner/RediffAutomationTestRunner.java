package com.cts.miniproject.testrunner;

import com.cts.miniproject.browserutils.BrowserFactory;
import com.cts.miniproject.frameworkutils.CommonUtils;
import com.cts.miniproject.frameworkutils.PropertiesFileReader;
import com.cts.miniproject.frameworkutils.ReadAndWriteFromExcel;
import com.cts.miniproject.pageobjects.CreateAccountPage;
import com.cts.miniproject.pageobjects.HomePage;
import com.cts.miniproject.pageobjects.TermsAndConditionPage;
import com.cts.miniproject.seleniumutils.ScreenShotUtil;
import com.cts.miniproject.testlistener.MyListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

@Listeners(MyListener.class)
public class RediffAutomationTestRunner {
    public static WebDriver driver;
    HomePage homepage = null;
    CreateAccountPage accountpage = null;
    TermsAndConditionPage childpage = null;
    String bn = null;
    String wr = null;
    String url = null;
    String remoteip = null;

    @BeforeMethod
    public void init() {
        try {
            bn = PropertiesFileReader.getPropertyValue("config", "browsername");
            wr = PropertiesFileReader.getPropertyValue("config", "wheretorun");
            remoteip = PropertiesFileReader.getPropertyValue("config", "hubip");
            url = PropertiesFileReader.getPropertyValue("config", "url");
            driver = BrowserFactory.getBrowser(bn, wr, remoteip);
            BrowserFactory.OpenUrl(url);
            homepage = new HomePage(driver);
            accountpage = new CreateAccountPage(driver);
            childpage = new TermsAndConditionPage(driver);
            homepage.clickOnCreateAnAccountButton();
        } catch (Exception e) {
            ScreenShotUtil.takeScreenShot(driver);
            e.printStackTrace();
        }
    }

    @Test(priority = 0)
    public void validateCreateAccountPageOpened() {
        try {
            Assert.assertTrue(accountpage.logoIsDisplayed(), "Page did not open as the logo is not displayed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "excelTestData", dataProviderClass = ReadAndWriteFromExcel.class, priority = 1)
    public void findNoOfLinksInPageAndPrintIt(String expectedNoOfLinks) {
        try {
            int actualNoOfLinks = accountpage.noOfLinks();
            System.out.println("Total Number of Links in the page is : " + actualNoOfLinks);
            System.out.println("Links : ");
            List<String> links = accountpage.Links();
            ReadAndWriteFromExcel.writeLinksData(links);
            System.out.println(links);
            Assert.assertEquals(actualNoOfLinks, Integer.parseInt(expectedNoOfLinks), "Number of Links are not same");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 2)
    public void validateChildWindowIsOpened() {
        try {
            accountpage.clickOnTermsAndConditionsLink();
            List<String> windId = accountpage.getWindowId();
            Assert.assertTrue(windId.size() == 2, "Child Window did not get opened");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "excelTestData", dataProviderClass = ReadAndWriteFromExcel.class, priority = 3)
    public void validateTheTitleOfChildWindow(String expectedTitle) {
        try {
            accountpage.clickOnTermsAndConditionsLink();
            List<String> windId = accountpage.getWindowId();
            accountpage.switchTabs(windId.get(1));
            childpage.maximizePage();
            String actualTitle = childpage.getPageTitle();
            Assert.assertEquals(actualTitle, expectedTitle, "Child Page Title did not match with the expected Title");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dataProvider = "excelTestData", dataProviderClass = ReadAndWriteFromExcel.class, priority = 4)
    public void switchToParentWindow(String expectedTitle) {
        try {
            accountpage.clickOnTermsAndConditionsLink();
            List<String> windId = accountpage.getWindowId();
            accountpage.switchTabs(windId.get(1));
            childpage.maximizePage();
            CommonUtils.sureWait(2);
            childpage.closePage();
            CommonUtils.sureWait(2);
            accountpage.switchTabs(windId.get(0));
            String actualTitle = accountpage.getPageTitle();
            Assert.assertEquals(actualTitle, expectedTitle, "Did not switch to Parent Page");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void end() {
        CommonUtils.sureWait(3);
        driver.quit();
    }
}
