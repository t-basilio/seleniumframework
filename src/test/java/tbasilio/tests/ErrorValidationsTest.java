package tbasilio.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import tbasilio.tests.testcomponents.BaseTest;
import tbasilio.pages.CartPage;
import tbasilio.pages.ProductCataloguePage;

import java.io.IOException;

public class ErrorValidationsTest extends BaseTest {


    @Test(groups = {"errorHandling"})
    public void loginErrorValidation() throws IOException {

        ProductCataloguePage cataloguePage = landingPage
                .loginApplication("anshika@gmail.com", "Iamki000");

        Assert.assertEquals(landingPage.getErrorMessage(), "Incorrect email or password.");
    }

    @Test
    public void productErrorValidation() throws IOException {

        String productName = "LAPTOP";
        ProductCataloguePage cataloguePage = landingPage
                .loginApplication("rahulshetty@gmail.com", "Iamking@000");

        var products = cataloguePage.getProductList();
        products.forEach(p -> System.out.println(p.getText()));
        cataloguePage.addProductToCart(productName);

        CartPage cartPage = cataloguePage.goToCartPage();
        Assert.assertFalse(cartPage.verifyProductDisplay(productName+"AA"));
    }

}
