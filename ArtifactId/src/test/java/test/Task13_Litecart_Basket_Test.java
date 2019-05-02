package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.setOut;
import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;


public class Task13_Litecart_Basket_Test {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
        driver.get("http://localhost/litecart/en/");
        driver.manage().window().maximize();
    }

    @Test
    public void basketTest() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        for (int i = 0; i < 3; i++) {
            driver.findElements(By.xpath("//li[contains(@class, 'product')]")).get(0).click();
            String header = driver.findElement(By.xpath("//h1[@class='title']")).getText();
            if (header.contains("Yellow Duck")) {
                new Select(driver.findElement(By.xpath("//select[@name='options[Size]']"))).selectByVisibleText("Small");
            }
            driver.findElement(By.xpath("//button[@name='add_cart_product']")).click();
            wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//span[@class='quantity']")), String.valueOf(i + 1)));
            driver.navigate().back();
        }
        driver.findElement(By.xpath("//a[contains(.,'Checkout »')]")).click();

        int size = driver.findElements(By.xpath("//table[contains(@class,'dataTable')]//tr")).size() - 5;

        for (int i = 0; i < size; i++) {
            driver.findElement(By.xpath("//button[@value='Remove']")).click();
            wait.until(stalenessOf(driver.findElement(By.xpath("//div[@id='box-checkout-summary']"))));
      }
        System.out.println("");
    }


    @After
    public void closeChrome() {
        driver.quit();
        driver = null;
    }
}
