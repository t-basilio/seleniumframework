package tbasilio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tbasilio.pages.pagecomponents.AbstractPageComponent;

import java.util.List;

public class ProductCataloguePage extends AbstractPageComponent {

    WebDriver driver;

    public ProductCataloguePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".mb-3")
    List<WebElement> products;

    @FindBy(css = ".ng-animating")
    WebElement spinner;

    By productsBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage = By.id("toast-container");

    public List<WebElement> getProductList() {
        waitForElementToApper(productsBy);
        return products;
    }

    public WebElement getProductByName(String productName) {
        return getProductList().stream().filter(p -> p.findElement(By.tagName("b"))
                .getText().equals(productName)).findFirst().orElse(null);
    }

    public void addProductToCart(String productName) {
        WebElement prod = getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToApper(toastMessage);
        waitElementToDesapper(spinner);
    }
}
