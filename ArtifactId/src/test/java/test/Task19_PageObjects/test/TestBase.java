package test.Task19_PageObjects.test;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import test.Task19_PageObjects.pages.BasketPage;
import test.Task19_PageObjects.pages.MainPage;
import test.Task19_PageObjects.pages.ProductPage;

import java.util.concurrent.TimeUnit;

public class TestBase {

    private WebDriver driver;
    BasketPage basketPage;
    ProductPage productPage;
    MainPage mainPage;


        @Before
        public void start() {
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
            driver.get("http://localhost/litecart/en/");
            driver.manage().window().maximize();
//            basketPage = new BasketPage(driver);
//            productPage = new ProductPage(driver);
            mainPage = new MainPage(driver);
        }
        @After
        public void closeChrome() {
            driver.quit();
            driver = null;
        }
    }

