package tbasilio.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import tbasilio.pagecomponents.AbstractPageComponent;

public class LandingPage extends AbstractPageComponent {

    WebDriver driver;

    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //WebElement userEmail = driver.findElement(By.id("userEmail"));
    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id = "userPassword")
    WebElement password;

    @FindBy(id = "login")
    WebElement submit;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;

    public ProductCataloguePage loginApplication(String email, String password) {
        this.userEmail.sendKeys(email);
        this.password.sendKeys(password);
        this.submit.click();
        return new ProductCataloguePage(driver);
    }

    public String getErrorMessage() {
        waitForWebElementToApper(errorMessage);
        return errorMessage.getText();
    }

    public void goTo() {
        driver.get("https://rahulshettyacademy.com/client");
    }
}
