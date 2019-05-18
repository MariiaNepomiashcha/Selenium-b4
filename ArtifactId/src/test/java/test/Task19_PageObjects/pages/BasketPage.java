package test.Task19_PageObjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;

public class BasketPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasketPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//table[contains(@class,'dataTable')]//tr")
    List<WebElement> table;

    @FindBy(xpath = "//button[@value='Remove']")
    WebElement buttonRemove;

    public void removeAllSelectedItemsFromBasket(){
        int size = table.size() - 5;

        for (int i = 0; i < size; i++) {
            buttonRemove.click();
            wait.until(stalenessOf(driver.findElement(By.xpath("//div[@id='box-checkout-summary']"))));
            System.out.println(i);
        }
    }
}
