package tbasilio.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tbasilio.pagecomponents.AbstractPageComponent;

import java.util.List;

public class OrdersPage extends AbstractPageComponent {

    WebDriver driver;

    @FindBy(css = ".totalRow button")
    WebElement checkoutElement;

    @FindBy(css = "tr td:nth-child(3)")
    List<WebElement> productsNames;

    public OrdersPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean verifyOrdersDisplay(String producName) {
        return productsNames.stream()
                .anyMatch(p -> p.getText().equalsIgnoreCase(producName));
    }

}
