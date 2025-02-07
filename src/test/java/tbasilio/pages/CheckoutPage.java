package tbasilio.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tbasilio.pagecomponents.AbstractPageComponent;

public class CheckoutPage extends AbstractPageComponent {

    WebDriver driver;
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[placeholder='Select Country']")
    WebElement country;

    @FindBy(css = ".action__submit")
    WebElement submit;

    @FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
    WebElement selectCountry;

    By results = By.cssSelector(".ta-results");

    public void selectingCountry(String countryName) {
        new Actions(driver).sendKeys(country, countryName).build().perform();
        waitForElementToApper(results);
        selectCountry.click();
    }

    public ConfirmationPage submitOrder() {
        submit.click();
        return new ConfirmationPage(driver);
    }

}
