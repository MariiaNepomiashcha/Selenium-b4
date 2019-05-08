package test;

import com.google.common.collect.Ordering;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.Collator;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class Task14_LitecartAdmin_LinksAreOpenedInNewWindows {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

    }

    @Test
    public void linksAreOpenInNewWindowCheck() {
        loginAsAdminToGoCountries();
        driver.findElement(By.xpath("//a[contains(.,'Add New Country')]")).click();


        By links = By.xpath("//i[@class='fa fa-external-link']");

        int linksCount = driver.findElements(links).size();
        for (int i = 0; i < linksCount; i++) {
            String mainWindow = driver.getWindowHandle();
            WebElement currentLink = driver.findElements(links).get(i);
            Set<String> openedWindowsBeforeClick = driver.getWindowHandles();
            currentLink.click();
            Set<String> openedWindowsAfterClick = driver.getWindowHandles();

            openedWindowsAfterClick.removeAll(openedWindowsBeforeClick);
            String newWindow = openedWindowsAfterClick.iterator().next();
            driver.switchTo().window(newWindow);

            driver.close();
            driver.switchTo().window(mainWindow);
        }

//i[@class='fa fa-external-link']

    }


    void loginAsAdminToGoCountries() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
        driver.manage().window().maximize();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).sendKeys(Keys.ENTER);
    }


    @After
    public void closeChrome() {
        driver.quit();
        driver = null;
    }

}
