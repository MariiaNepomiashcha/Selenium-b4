package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class LitecartAdminTest {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
    }

    @Test
    public void menuSubMenuCheck() {
        loginAsAdmin();

        By menuItems = By.xpath("//ul[@id='box-apps-menu']/li");
        By subMenuItems = By.xpath("//ul[@id='box-apps-menu']/li[@class='selected']//span[@class='name']");
        By headerLocator = By.xpath("//h1");


        int menuItemsSize = driver.findElements(menuItems).size();
        for (int i = 0; i < menuItemsSize; i++) {
            WebElement currentMenuItem = driver.findElements(menuItems).get(i);
            String currentMenuName = "Menu #" + (i + 1) + " = " + currentMenuItem.getText();
            currentMenuItem.click();
            checkHeaderIsPresent(driver, headerLocator, currentMenuName);

            int subMenuItemsSizeForCurrentMenu = driver.findElements(subMenuItems).size();
            for (int k = 1; k < subMenuItemsSizeForCurrentMenu; k++) {
                WebElement currentSubMenuItem = driver.findElements(subMenuItems).get(k);
                String currentSubMenuName = "SubMenu #" + k + "(from " + currentMenuName + ") = " + currentSubMenuItem.getText();
                currentSubMenuItem.click();
                checkHeaderIsPresent(driver, headerLocator, currentSubMenuName);
            }

        }


    }


    void loginAsAdmin() {
        driver.get("http://localhost/litecart/admin/");
        driver.manage().window().maximize();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).sendKeys(Keys.ENTER);
    }

    boolean checkHeaderIsPresent(WebDriver driver, By locator, String currentMenuSubMenuItem) {

        int headerCount = driver.findElements(locator).size();
        if (headerCount > 0) {
            System.out.println("exactly " + headerCount + " Header is present for " + currentMenuSubMenuItem);
            return true;
        } else {
            System.out.println("Ohhh no!! Header is NOT present for " + currentMenuSubMenuItem);
            return false;
        }
    }

    @After
    public void closeChrome() {
        driver.quit();
        driver = null;
    }

}
