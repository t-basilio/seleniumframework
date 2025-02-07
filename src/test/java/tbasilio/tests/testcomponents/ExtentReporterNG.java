package tbasilio.tests.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

    public static ExtentReports getReportObject() {
        String reportPath = "src/test/resources/reports/index.html";
        var reporter = new ExtentSparkReporter(reportPath);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        var extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Thiago Basilio");
        return extent;
    }
}
