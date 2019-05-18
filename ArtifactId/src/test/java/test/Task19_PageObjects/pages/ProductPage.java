package test.Task19_PageObjects.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driver, this);
    }



    @FindBy(xpath = "//h1[@class='title']")
    WebElement title;

    @FindBy(xpath = "//select[@name='options[Size]']")
    WebElement size;

    @FindBy(xpath = "//button[@name='add_cart_product']")
    WebElement buttonAddProduct;

    @FindBy(xpath = "//span[@class='quantity']")
    WebElement basketCount;

    @FindBy(xpath = "//div[@id='logotype-wrapper']")
    WebElement litecartHeader;

    public void addSelectedItemToBasket(int i) {
        String header = title.getText();
        if (header.contains("Yellow Duck")) {
            new Select(size).selectByVisibleText("Small");
        }
        buttonAddProduct.click();
        wait.until(ExpectedConditions.textToBePresentInElement(basketCount, String.valueOf(i + 1)));
    }


    public MainPage returnToMainPage() {
        litecartHeader.click();
        return new MainPage(driver);
    }
}
