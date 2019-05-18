package test.Task19_PageObjects.test;

import org.junit.Test;


public class Basket_Test extends TestBase{

    @Test
    public void basketTest() {
        for (int i = 0; i < 3; i++) {
            productPage = mainPage.selectFirstItem();
            productPage.addSelectedItemToBasket(i);
            mainPage = productPage.returnToMainPage();
        }
        basketPage = mainPage.openBasket();
        basketPage.removeAllSelectedItemsFromBasket();
    }

}
