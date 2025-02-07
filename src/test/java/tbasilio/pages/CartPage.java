package tbasilio.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tbasilio.pagecomponents.AbstractPageComponent;

import java.util.List;

public class CartPage extends AbstractPageComponent {

    WebDriver driver;

    @FindBy(css = ".totalRow button")
    WebElement checkoutElement;

    @FindBy(css = ".cartSection h3")
    List<WebElement> cartProducts;

    public CartPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean verifyProductDisplay(String producName) {
        return cartProducts.stream()
                .anyMatch(p -> p.getText().equalsIgnoreCase(producName));
    }

    public CheckoutPage goToCheckout() {
        checkoutElement.click();
        return new CheckoutPage(driver);
    }

}
