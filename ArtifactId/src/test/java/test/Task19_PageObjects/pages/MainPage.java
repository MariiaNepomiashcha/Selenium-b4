package test.Task19_PageObjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }
    @FindBy(xpath = "//li[contains(@class, 'product')]")
    List<WebElement> products;

    @FindBy(xpath = "//a[contains(.,'Checkout »')]")
    WebElement buttonOpenBasket;

    public ProductPage selectFirstItem(){
        products.get(0).click();
        return new ProductPage(driver);
    }

    public BasketPage openBasket() {
        buttonOpenBasket.click();
        return new BasketPage(driver);
    }


}
