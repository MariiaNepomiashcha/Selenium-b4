package test;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Task3_EnvironmentTest {

    @Test
    public void googleSearch (){

        //WebDriver driver = new ChromeDriver();

        DesiredCapabilities caps = new DesiredCapabilities();
      //  caps.setCapability("unexpectedAlertBehaviour", "dismiss");
        WebDriver driver = new ChromeDriver(caps);
        System.out.println(((HasCapabilities) driver).getCapabilities());

//        WebElement form1 = driver.findElement(By.id("login-form"));
//        WebElement form2 = driver.findElement(By.tagName("form"));
//        WebElement form3 = driver.findElement(By.className("login"));
//        WebElement form4 = driver.findElement(By.cssSelector("form.login"));
//        WebElement form5 = driver.findElement(By.cssSelector("#login-form"));
//
//        WebElement field1 = driver.findElement(By.name("username"));
//        WebElement field2 = driver.findElement(By.xpath("//input[@name='username']"));
//        WebElement link = driver.findElement(By.linkText("Logout"));

        driver.get("http://www.google.com/");
        driver.manage().window().maximize();
        driver.findElement(By.name("q")).sendKeys("Mariia Nepomiashcha Facebook");
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
        driver.quit();
    }

}
