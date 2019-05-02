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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;

public class Task11_Litecart_CreateNewUserAccount_Test {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
        driver.get("http://localhost/litecart/en/");
        driver.manage().window().maximize();
    }

    @Test
    public void createNewUserAccount() {
        String uniqueMail = currentTimeMillis() + "@mariia.com";
        System.out.println("current e-mail = " + uniqueMail);
        driver.findElement(By.xpath("//a[contains(., 'New customers click here')]")).click();
        driver.findElement(By.xpath("//input[@name = 'firstname']")).sendKeys("Maria");
        driver.findElement(By.xpath("//input[@name = 'lastname']")).sendKeys("Nepomiashcha");
        driver.findElement(By.xpath("//input[@name = 'address1']")).sendKeys("United States");
        driver.findElement(By.xpath("//input[@name = 'postcode']")).sendKeys("12345");
        driver.findElement(By.xpath("//input[@name = 'city']")).sendKeys("New York");

        new Select(driver.findElement(By.xpath("//select[@name= 'country_code']"))).selectByVisibleText("United States");

        By state = (By.xpath("//select[@name= 'zone_code']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement selectState = wait.until(ExpectedConditions.elementToBeClickable(state));
        new Select(selectState).selectByVisibleText("New York");

        driver.findElement(By.xpath("//input[@name= 'email']")).sendKeys(uniqueMail);
        driver.findElement(By.xpath("//input[@name = 'phone']")).sendKeys("123456789");
        driver.findElement(By.xpath("//input[@name = 'password']")).sendKeys("admin");
        driver.findElement(By.xpath("//input[@name = 'confirmed_password']")).sendKeys("admin");

        driver.findElement(By.xpath("//button[@name = 'create_account']")).click();
        System.out.println(driver.findElement(By.xpath("//div[contains(@class, 'notice')]")).getText());

        driver.findElement(By.xpath("//div[@id = 'box-account']//a[contains(.,'Logout')]")).click();
        System.out.println(driver.findElement(By.xpath("//div[contains(@class, 'notice')]")).getText());

        driver.findElement(By.xpath("//input[@name= 'email']")).sendKeys(uniqueMail);
        driver.findElement(By.xpath("//input[@name = 'password']")).sendKeys("admin");
        driver.findElement(By.xpath("//button[@name = 'login']")).click();
        System.out.println(driver.findElement(By.xpath("//div[contains(@class, 'notice')]")).getText());

        driver.findElement(By.xpath("//div[@id = 'box-account']//a[contains(.,'Logout')]")).click();
        System.out.println(driver.findElement(By.xpath("//div[contains(@class, 'notice')]")).getText());
    }


    @After
    public void closeChrome() {
        driver.quit();
        driver = null;
    }
}
