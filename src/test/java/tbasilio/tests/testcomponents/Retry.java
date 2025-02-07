package tbasilio.tests.testcomponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {


    private final int maxRetry = Integer
            .parseInt(InitialProperties.getProperty("maxRetry"));

    private int counter = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {
        return maxRetry > counter++;
    }
}
