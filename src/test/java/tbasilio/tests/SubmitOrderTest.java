package tbasilio.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tbasilio.tests.testcomponents.BaseTest;
import tbasilio.pages.*;
import tbasilio.tests.testcomponents.Retry;

import java.io.IOException;
import java.util.*;

public class SubmitOrderTest extends BaseTest {
    String productName = "IPHONE";

    @Test(dataProvider = "getData", groups = {"purchase"}, retryAnalyzer = Retry.class)
    public void submitOrder(HashMap<String, String> input) throws IOException {

        ProductCataloguePage cataloguePage = landingPage
                .loginApplication(input.get("email"), input.get("password"));

        var products = cataloguePage.getProductList();
        System.out.println("Products on Catalogue Page");
        products.forEach(p -> System.out.println(p.getText()));

        cataloguePage.addProductToCart(input.get("product"));

        CartPage cartPage = cataloguePage.goToCartPage();
        Assert.assertTrue(cartPage.verifyProductDisplay(input.get("product")));

        CheckoutPage checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectingCountry("india");

        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        Assert.assertTrue(confirmationPage.getConfirmationMessage()
                .equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods = {"submitOrder"})
    public void orderHistoryTest() {
        ProductCataloguePage cataloguePage = landingPage
                .loginApplication("automation@selenium.com", "Selenium@123");
        OrdersPage ordersPage = cataloguePage.goToOrdersPage();

        Assert.assertTrue(ordersPage.verifyOrdersDisplay(productName));
    }

    @DataProvider
    public Object[][] getData() throws IOException {

        List<HashMap<String, String>> data = getJsonDataToMap("src/test/java/tbasilio/data/purchaseOrder.json");
        // you can add manually HashMaps like this: data.get(0), data.get(1) ...etc
        return data.stream()
                .map(hp -> new HashMap[]{hp})
                .toArray(HashMap[][]::new);
        /*
        var map = new HashMap<String, String>();
        map.put("email", "anshika@gmail.com");
        map.put("password", "Iamking@000");
        map.put("product", "IPHONE 13 PRO");

        var map1 = new HashMap<String, String>();
        map1.put("email", "shetty@gmail.com");
        map1.put("password", "Iamking@000");
        map1.put("product", "BANARSI SAREE");

        return new Object[][] { {map}, {map1} };

        return new Object[][] {
                {"anshika@gmail.com", "Iamking@000", "IPHONE 13 PRO"},
                {"shetty@gmail.com", "Iamking@000", "BANARSI SAREE"}
            };
        */
    }
}
