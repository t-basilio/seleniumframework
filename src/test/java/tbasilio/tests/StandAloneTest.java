package tbasilio.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class StandAloneTest {

    public static void main(String[] args) {

        String productName = "BANARSI SAREE";
        WebDriver driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client");

        driver.findElement(By.id("userEmail")).sendKeys("anshika@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Iamking@000");
        driver.findElement(By.id("login")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        var products = driver.findElements(By.cssSelector(".mb-3"));

        WebElement prod = products.stream().filter(p -> p.findElement(By.tagName("b"))
                .getText().equals(productName)).findFirst().orElse(null);

        if(prod !=null)
            prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        var cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));

        Assert.assertTrue(cartProducts.stream().anyMatch(cp -> cp.getText().equalsIgnoreCase(productName)));

        driver.findElement(By.cssSelector(".totalRow button")).click();

        new Actions(driver)
                .sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")),"india")
                .build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();

        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();
    }
}
