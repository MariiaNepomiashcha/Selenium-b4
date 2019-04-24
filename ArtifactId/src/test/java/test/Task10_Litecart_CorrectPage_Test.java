package test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class Task10_Litecart_CorrectPage_Test {

    private WebDriver driver;

    @Before
    public void start() {
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);
        driver.get("http://localhost/litecart/en/");
        driver.manage().window().maximize();
    }

    @Test
    public void correctPageTest() {


        By itemsInCampaignsBlock = By.xpath("//div[@id='box-campaigns']//li");
        By regularPrice = By.xpath(".//s[@class='regular-price']");
        By campaignPrice = By.xpath(".//strong[@class='campaign-price']");
        By itemName = By.xpath(".//div[@class='name']");
        By openedItem = By.xpath("//div[@id='box-product']");
        By header = By.xpath(".//h1[@class='title']");

        WebElement firstItemInCampaignsBlock = driver.findElements(itemsInCampaignsBlock).get(0);
        WebElement mainPageRegularPrice = firstItemInCampaignsBlock.findElement(regularPrice);
        WebElement mainPageCampaignPrice = firstItemInCampaignsBlock.findElement(campaignPrice);

        String mainPageRegularPriceText = mainPageRegularPrice.getText();
        String mainPageCampaignPriceText = mainPageCampaignPrice.getText();
        String mainPageItemName = firstItemInCampaignsBlock.findElement(itemName).getText();

        String mainPageRegularPriceTextDecoration = mainPageRegularPrice.getCssValue("text-decoration");
        String mainPageRegularPriceColor = mainPageRegularPrice.getCssValue("color");
        mainPageRegularPriceColor = checkColor(mainPageRegularPriceColor);

        String mainPageCampaignPriceFont = mainPageCampaignPrice.getCssValue("font-weight");
        String mainPageCampaignPriceColor = mainPageCampaignPrice.getCssValue("color");
        mainPageCampaignPriceColor = checkColor(mainPageCampaignPriceColor);

        String mainPageRegularPriceFontSize = mainPageRegularPrice.getCssValue("font-size");
        String mainPageCampaignPriceFontSize = mainPageCampaignPrice.getCssValue("font-size");


        firstItemInCampaignsBlock.click();


        WebElement openedProduct = driver.findElement(openedItem);
        WebElement openedPageRegularPrice = openedProduct.findElement(regularPrice);
        WebElement openedPageCampaignPrice = openedProduct.findElement(campaignPrice);

        String openedPageRegularPriceText = openedPageRegularPrice.getText();
        String openedPageCampaignPriceText = openedPageCampaignPrice.getText();
        String openedPageItemName = openedProduct.findElement(header).getText();

        String openedPageRegularPriceTextDecoration = openedPageRegularPrice.getCssValue("text-decoration");
        String openedPageRegularPriceColor = openedPageRegularPrice.getCssValue("color");
        openedPageRegularPriceColor = checkColor(openedPageRegularPriceColor);


        String openedPageCampaignPriceFont = openedPageCampaignPrice.getCssValue("font-weight");
        String openedPageCampaignPriceColor = openedPageCampaignPrice.getCssValue("color");
        openedPageCampaignPriceColor = checkColor(openedPageCampaignPriceColor);

        compareTwoStringValues(mainPageItemName, openedPageItemName, "Check a :main/opened Page Item Name");
        compareTwoStringValues(mainPageRegularPriceText, openedPageRegularPriceText, "Check b :main/opened Page Regular Price Text");
        compareTwoStringValues(mainPageCampaignPriceText, openedPageCampaignPriceText, "Check b :main/opened Page Campaign Price Text");

        mainPageRegularPriceTextDecoration = returnSecondStringIfItIsAPartOfFirstString(mainPageRegularPriceTextDecoration, "line-through");
        openedPageRegularPriceTextDecoration = returnSecondStringIfItIsAPartOfFirstString(openedPageRegularPriceTextDecoration, "line-through");

        compareTwoStringValues(mainPageRegularPriceTextDecoration, openedPageRegularPriceTextDecoration, "Check v :main/opened Page Regular Price TextDecoration");
        compareTwoStringValues(mainPageRegularPriceColor, openedPageRegularPriceColor, "Check v :main/opened Page Regular Price Color");

        mainPageCampaignPriceFont = checkBold(mainPageCampaignPriceFont);
        openedPageCampaignPriceFont = checkBold(openedPageCampaignPriceFont);

        compareTwoStringValues(mainPageCampaignPriceFont, openedPageCampaignPriceFont, "Check g :main/opened Page Campaign Price Font");
        compareTwoStringValues(mainPageCampaignPriceColor, openedPageCampaignPriceColor, "Check g :main/opened Page Campaign Price Color");

        String openedPageRegularPriceFontSize = openedPageRegularPrice.getCssValue("font-size");
        String openedPageCampaignPriceFontSize = openedPageCampaignPrice.getCssValue("font-size");

        compareFontSize(mainPageRegularPriceFontSize, mainPageCampaignPriceFontSize, "Check d :main Page Regular/Campaign Price Font Size");
        compareFontSize(openedPageRegularPriceFontSize, openedPageCampaignPriceFontSize, "Check d :opened Page Regular/Campaign Price Font Size");

    }

    private String returnSecondStringIfItIsAPartOfFirstString(String str1, String str2) {
        if (str1.contains(str2)) {
            str1 = str2;
        } else {
            System.out.println(str1 +"does NOT contain"+ str2);
        }
        return str1;
    }

    private String checkBold(String str) {
        if (Integer.parseInt(str) >= 700) {
            str = "bold";

        } else {
            System.out.println("NOT BOLD");
        }
        return str;
    }

    private void compareTwoStringValues(String str1, String str2, String explanation) {
        if (str1.equals(str2)) {
            System.out.println(explanation + " --> _as expected_ Values are the same and equals = " + str1);
        } else {
            System.out.println(explanation + " -->!!!!ATTENTION!!!! Values are different. Value1 = " + str1 + " Value2 = " + str2);
        }
    }


    private void compareFontSize(String expectedSmallerValue, String expectedBiggerValue, String explanation) {
        Float firstValue_expectedAsSmaller = Float.valueOf(expectedSmallerValue.substring(0, expectedSmallerValue.length() - 2));
        Float secondValue_expectedAsBigger = Float.valueOf(expectedBiggerValue.substring(0, expectedBiggerValue.length() - 2));

        if (secondValue_expectedAsBigger > firstValue_expectedAsSmaller) {
            System.out.println(explanation + " -->  _as expected_ Value1 = " + expectedSmallerValue + " is smaller than Value2 = " + expectedBiggerValue);
        } else {
            System.out.println(explanation + " -->!!!!ATTENTION!!!! Value1 = " + expectedSmallerValue + " is bigger than Value2 = " + expectedBiggerValue);
        }
    }


    private String checkColor(String rgbFormat) {
        int r = 1;
        int g = 2;
        int b = 3;
        Pattern rgb = Pattern.compile("[a-z]*\\(*([0-9]+), *([0-9]+), *([0-9]+) *(,*.[0-9]*)*\\)");
        Matcher m = rgb.matcher(rgbFormat);

        if (m.matches()) {
            r = parseInt(m.group(1));
            g = parseInt(m.group(2));
            b = parseInt(m.group(3));
        }


        if (r == g && g == b) {
            return "GREY COLOR";
        } else if (g == 0 && b == 0) {
            return "RED COLOR";
        } else {
            return "Do not know the color";
        }

    }

    @After
    public void closeChrome() {
        driver.quit();
        driver = null;
    }

}
