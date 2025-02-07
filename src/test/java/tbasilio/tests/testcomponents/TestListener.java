package tbasilio.tests.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener extends BaseTest implements ITestListener {

    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); //thread safe

    @Override
    public void onStart(ITestContext context) {
        test = extent.createTest(context.getName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
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

        extentTest.get().addScreenCaptureFromPath(filePath, result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
