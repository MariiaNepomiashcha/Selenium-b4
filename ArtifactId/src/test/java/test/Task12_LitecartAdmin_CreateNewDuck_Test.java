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
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static java.lang.System.currentTimeMillis;

public class Task12_LitecartAdmin_CreateNewDuck_Test {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

    }

    @Test
    public void createNewDuck() {
        loginAsAdmin();

        String uniqueDuck = currentTimeMillis() + " Duck";
        driver.findElement(By.xpath("//span[contains(.,'Catalog')]")).click();
        driver.findElement(By.xpath("//a[contains(.,'Add New Product')]")).click();

        driver.findElement(By.xpath("//label[contains(.,'Enabled')]")).click();
        driver.findElement(By.xpath("//input[@name='name[en]']")).sendKeys(uniqueDuck);


        WebElement downloadPhoto = driver.findElement(By.xpath("//input[@name='new_images[]']"));
        File file = new File("src/test/resources/duck.jpg");
        downloadPhoto.sendKeys(file.getAbsolutePath());

        driver.findElement(By.xpath("//input[@name='quantity']")).sendKeys("100");
        new Select(driver.findElement(By.xpath("//select[@name='sold_out_status_id']"))).selectByVisibleText("-- Select --");

        driver.findElement(By.xpath("//a[contains(.,'Information')]")).click();
        new Select(driver.findElement(By.xpath("//select[@name='manufacturer_id']"))).selectByVisibleText("ACME Corp.");
        driver.findElement(By.xpath("//input[@name='keywords']")).sendKeys("Keywords");
        driver.findElement(By.xpath("//input[@name='short_description[en]']")).sendKeys("Short Description");
        driver.findElement(By.xpath("//div[@class='trumbowyg-editor']")).sendKeys("Description");
        driver.findElement(By.xpath("//input[@name='head_title[en]']")).sendKeys("Head Title");

        driver.findElement(By.xpath("//a[contains(.,'Prices')]")).click();
        new Select(driver.findElement(By.xpath("//select[@name='purchase_price_currency_code']"))).selectByVisibleText("US Dollars");
        driver.findElement(By.xpath("//input[@name='purchase_price']")).sendKeys("10");
        driver.findElement(By.xpath("//input[@name='prices[USD]']")).sendKeys("100");
        driver.findElement(By.xpath("//button[@name='save']")).click();

        driver.findElement(By.xpath("//a[contains(.,'" + uniqueDuck + "')]"));

        System.out.println(uniqueDuck + " is present");
    }

    void loginAsAdmin() {
        driver.get("http://localhost/litecart/admin/");
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
