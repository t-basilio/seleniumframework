package tbasilio.pages.pagecomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tbasilio.pages.CartPage;
import tbasilio.pages.OrdersPage;

import java.time.Duration;

public abstract class AbstractPageComponent {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "[routerlink*='cart']")
    WebElement cartHeader;

    @FindBy(css = "[routerlink*='myorders']")
    WebElement orderHeader;

    public AbstractPageComponent(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void waitForElementToApper(By findBy) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }

    public void waitForWebElementToApper(WebElement findBy) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }

    public void waitElementToDesapper(WebElement element) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public CartPage goToCartPage() {
        cartHeader.click();
        return new CartPage(driver);
    }

    public OrdersPage goToOrdersPage() {
        orderHeader.click();
        return new OrdersPage(driver);
    }
}
