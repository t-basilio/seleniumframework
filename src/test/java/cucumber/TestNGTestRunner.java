package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/cucumber", glue = "tbasilio.stepdefinitions",
        monochrome = true, plugin = { "html:target/cucumber.html"},
        tags = "@regression"
)
public class TestNGTestRunner extends AbstractTestNGCucumberTests {

}
