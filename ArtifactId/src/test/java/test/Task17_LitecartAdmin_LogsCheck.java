package test;

import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static org.junit.Assert.assertEquals;

public class Task17_LitecartAdmin_LogsCheck {

    //    private WebDriver driver;
    private EventFiringWebDriver driver;

    public static class MyListener extends AbstractWebDriverEventListener {
        @Override
        public void beforeFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by);
            driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
        }

        @Override
        public void afterFindBy(By by, WebElement element, WebDriver driver) {
            System.out.println(by + " found");
            driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
        }

        @Override
        public void onException(Throwable throwable, WebDriver driver) {
            System.out.println(throwable);
            File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screen = new File("screen-" + System.currentTimeMillis() + ".png");
            try {
                Files.copy(tempFile, screen);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(screen);
            driver.manage().logs().get("browser").forEach(l -> System.out.println(l));
        }
    }

    @Before
    public void start() {
//        DesiredCapabilities cap = DesiredCapabilities.chrome(); --PERFORMANCE
//        LoggingPreferences logPrefs = new LoggingPreferences(); -- https://sites.google.com/a/chromium.org/chromedriver/logging/performance-log
//        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
//        driver = new EventFiringWebDriver(new ChromeDriver(cap));
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.register(new MyListener());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

    }

    @Test
    public void openAllProducts() {
//            System.out.println(driver.manage().logs().getAvailableLogTypes()); --all Logs Type
        loginAsAdminToGoCatalog();
        driver.findElement(By.xpath("//a[contains(.,'Rubber Ducks')]")).click();
        driver.findElement(By.xpath("//a[contains(.,'Subcategory')]")).click();


        By products = By.xpath("//tr[contains(@class,'row')]//td[3]//a");

        int productsCount = driver.findElements(products).size();
        for (int i = 3; i < productsCount; i++) {
            WebElement currentLink = driver.findElements(products).get(i);
            currentLink.click();
            driver.navigate().back();
        }

    }


    void loginAsAdminToGoCatalog() {
        driver.get("http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1");
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
