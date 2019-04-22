package test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class Task8_LitecartStickerTest {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
        driver.get("http://localhost/litecart/en/");
        driver.manage().window().maximize();
    }

    @Test
    public void exactlyOneStickerIsPresentForEveryItem() {

        By itemsAll = By.xpath("//a[contains(@title, 'Duck') and @class='link']");
        By label = By.xpath(".//div[contains(@class, 'sticker')]");

        List<WebElement> itemsList = driver.findElements(itemsAll);

        for (WebElement currentItem : itemsList) {
            assertEquals(1,currentItem.findElements(label).size());
        }


    }


    @After
    public void closeChrome() {
        driver.quit();
        driver = null;
    }
}
