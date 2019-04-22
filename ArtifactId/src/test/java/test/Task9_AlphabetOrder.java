package test;

import com.google.common.collect.Ordering;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class Task9_AlphabetOrder {

    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MILLISECONDS);

    }

    @Test
    public void alphabetOrderCheck() {
        loginAsAdminToGoCountries();

        By table = By.xpath("//table[@class='dataTable']//tr[@class='row']");
        By tableNames = By.xpath(".//td[5]");
        By tableZones = By.xpath(".//td[6]");
        By zonesName = By.xpath("//input[contains(@name,'name') and @type!='text']");

        List<WebElement> tableItems = driver.findElements(table);
        List<String> actualOrder = new ArrayList<String>();
        for (int i = 0; i < tableItems.size(); i++) {
            WebElement currentItem = tableItems.get(i).findElement(tableNames);
            String currentItemName = currentItem.getText();
            actualOrder.add(currentItemName);
        }
        assertEquals(true, Ordering.natural().isOrdered(actualOrder));
        System.out.println("Table Items are in Alphabet Order");


        List<String> countryWithNonZeroZones = new ArrayList<String>();
        for (int i = 0; i < tableItems.size(); i++) {
            WebElement currentZones = tableItems.get(i).findElement(tableZones);
            int currentZonesNumberInTable = Integer.parseInt(currentZones.getText());
            if (currentZonesNumberInTable > 0) {
                countryWithNonZeroZones.add(tableItems.get(i).findElement(tableNames).getText());
            }
        }
        System.out.println("There are " + countryWithNonZeroZones.size() + " countries with non Zero Zones");

        for (int i = 0; i < countryWithNonZeroZones.size(); i++) {
            String currentCountryName = countryWithNonZeroZones.get(i);
            System.out.println(i + 1 + " Country is " + currentCountryName);
            By currentCountry = By.xpath("//a[contains(.,'" + currentCountryName + "')]");
            driver.findElement(currentCountry).click();

            List<String> actualZoneNamesOrder = new ArrayList<String>();
            for (int k = 0; k < driver.findElements(zonesName).size(); k++) {
                WebElement currentItem = driver.findElements(zonesName).get(k);
                String currentItemName = currentItem.getText();
                actualZoneNamesOrder.add(currentItemName);
            }
            assertEquals(true, Ordering.natural().isOrdered(actualZoneNamesOrder));
            System.out.println("Table Items for " + currentCountryName + " are in Alphabet Order");

            driver.navigate().back();
        }

        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");
        By geoZonesListOfCountries = By.xpath("//table[@class='dataTable']//td[3]/a");
        By selectedZones = By.xpath("//select[contains(@name,'zone_code')]//option[@selected='selected']");

        List<WebElement> geoZonesList = driver.findElements(geoZonesListOfCountries);
        for (int i = 0; i < geoZonesList.size(); i++) {
            geoZonesList = driver.findElements(geoZonesListOfCountries);
            WebElement currentGeoZone = geoZonesList.get(i);
            String currentGeoZoneName = currentGeoZone.getText();
            currentGeoZone.click();
            List<WebElement> selectedZonesList = driver.findElements(selectedZones);
            List<String> actualSelectedZoneNamesOrder = new ArrayList<String>();
            for (int k = 0; k < selectedZonesList.size(); k++){
                actualSelectedZoneNamesOrder.add(selectedZonesList.get(k).getText());
            }
            assertEquals(true, Ordering.natural().isOrdered(actualSelectedZoneNamesOrder));
            System.out.println("Table Items for GEO ZONE = " + currentGeoZoneName + " are in Alphabet Order");


            driver.navigate().back();

        }

    }


    void loginAsAdminToGoCountries() {
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");
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
