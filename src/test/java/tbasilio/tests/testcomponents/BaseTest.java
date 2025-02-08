package tbasilio.tests.testcomponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import tbasilio.pages.LandingPage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
public class BaseTest {

    protected final String PREFIXPATH = System.getProperty("user.dir");
    public WebDriver driver;
    public LandingPage landingPage;

    public WebDriver initializeDrive() throws IOException {

        String browserCLI = System.getProperty("browser");
        String browserName = browserCLI != null ? browserCLI : InitialProperties.getProperty("browser");
        String os = System.getProperty("os.name").contains("Windows") ? "windows" : "linux";
        String extension = !os.equals("windows") ? "" : ".exe";

        if(browserName.equalsIgnoreCase("chrome")) {

            System.getProperty("webdriver.chrome.driver",
                    "src/test/resources/drivers/"+ os +"/chromedriver" + extension);
            driver = new ChromeDriver(new ChromeOptions()
                    .addArguments("--user-data-dir=~/.config/google-chrome"));

        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {

            System.getProperty("webdriver.gecko.driver",
                    "src/test/resources/drivers/"+ os +"/geckodriver" + extension);
            driver = new FirefoxDriver();
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication() throws IOException {
        driver = initializeDrive();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        //read json to string, you can concat PREFIXPATH with filePath
        String jsonContent = FileUtils.readFileToString(
                new File(filePath), StandardCharsets.UTF_8
        );

        // string to HashMap with Jackson Databind
        var mapper = new ObjectMapper();
        var data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;
    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        // you can concat PREFIXPATH with filePaht
        String filePath = "src/test/resources/reports/".concat(testCaseName);
        var ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source, new File(filePath.concat(".png")));
        return filePath.concat(".png");
    }
}
