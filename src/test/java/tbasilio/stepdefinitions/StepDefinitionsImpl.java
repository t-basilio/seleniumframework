package tbasilio.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import tbasilio.pages.*;
import tbasilio.tests.testcomponents.BaseTest;

import java.io.IOException;

public class StepDefinitionsImpl extends BaseTest {

    public LandingPage landingPage;
    public ProductCataloguePage cataloguePage;
    public CartPage cartPage;
    CheckoutPage checkoutPage;
    ConfirmationPage confirmationPage;

    @Given("I landed on Ecommerce page")
    public void iLandedOnEcommercePage() throws IOException {
        landingPage = launchApplication();
    }

    @Given("^Logged in with username (.+) and password (.+)$")
    public void loggedInWithUsernameAndPassword(String username,  String password) {
        cataloguePage = landingPage.loginApplication(username, password);
    }

    @When("^I add product (.+) from Cart$")
    public void iAddProductFromCart(String productName) {
        var products = cataloguePage.getProductList();
        cataloguePage.addProductToCart(productName);
    }

    @And("^Checkout (.+) and submit the order$")
    public void checkoutProductAndSubmitTheOrder(String productName) {
        cartPage = cataloguePage.goToCartPage();
        Assert.assertTrue(cartPage.verifyProductDisplay(productName));

        checkoutPage = cartPage.goToCheckout();
        checkoutPage.selectingCountry("india");
        confirmationPage = checkoutPage.submitOrder();
    }

    @Then("{string} message is displayed on confirmation page")
    public void messageIsDisplayedOnConfirmationPage(String message) {
        Assert.assertTrue(confirmationPage.getConfirmationMessage()
                .equalsIgnoreCase(message));
        tearDown();
    }

    @Then("{string} message is displayed")
    public void messageIsDisplayed(String message) {
        Assert.assertEquals(landingPage.getErrorMessage(), message);
        tearDown();
    }
}
