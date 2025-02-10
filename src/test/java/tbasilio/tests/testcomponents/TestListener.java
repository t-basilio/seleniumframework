package tbasilio.tests.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.Arrays;

public class TestListener extends BaseTest implements ITestListener {

    ExtentTest test;
    String testName;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); //thread safe

    @Override
    public void onStart(ITestContext context) {
        test = extent.createTest(context.getName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testName = formatTestName(result.getName());

        String productName = convertParameterToProductName(result.getParameters());
        extentTest.get().log(Status.PASS, testName.concat(" " + productName));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        String testName = formatTestName(result.getName());
        String productName = convertParameterToProductName(result.getParameters());
        String filePath = null;
        try {
            //get driver on current context for pass argument to screenshot
            driver = (WebDriver) result.getTestClass().getRealClass()
                    .getField("driver").get(result.getInstance());

            filePath = getScreenshot(result.getName(), driver);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // attention for filePath: must have located in the same directory index.html
        if(filePath != null)
            filePath = filePath.substring(filePath.indexOf(result.getName()));

        extentTest.get().addScreenCaptureFromPath(filePath, testName+" "+productName);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private String formatTestName(String testName) {
        return testName.substring(0, 1).toUpperCase()
                .concat(testName.replaceAll("([A-Z])", " $1").substring(1));
    }

    private String convertParameterToProductName(Object[] parameters) {
        String productName = Arrays.stream(parameters)
                .map(Object::toString).findFirst().orElse("");

        if(!productName.isBlank() && productName.contains("product")) {
            return productName.split("product")[1]
                    .split(",")[0].substring(1);
        }

        return productName;
    }
}
