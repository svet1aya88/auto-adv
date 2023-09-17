package core;

import core.browser.DriverSingleton;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Listener extends TestListenerAdapter implements IInvokedMethodListener, ITestListener, IRetryAnalyzer {

    boolean testSuccess = true;

    int count = 1;
    int maxRetryCount = 2;

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            if (!testSuccess) {
                testResult.setStatus(ITestResult.FAILURE);
            }
        }
    }

    @Override
    public void onTestStart(final ITestResult tr) {
        super.onTestStart(tr);
        Logger.info("STARTED: {}", tr.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(final ITestResult tr) {
        super.onTestSuccess(tr);
        Logger.info("PASSED: {}", tr.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(final ITestResult tr) {
        super.onTestSkipped(tr);
        Logger.warn("SKIPPED: {}", tr.getMethod().getMethodName());
    }

    @Override
    public void onConfigurationFailure(final ITestResult tr) {
        super.onConfigurationFailure(tr);
        if (DriverSingleton.isDriverInitialized()) {
            DriverSingleton.quit();
        }
        Logger.warn("Test configuration failed.");
    }

    @Override
    public void onTestFailure(final ITestResult tr) {
        super.onTestFailure(tr);
        Logger.warn("FAILED: " + tr.getMethod().getMethodName());
    }

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetryCount) {
            count++;
            return true;
        }
        return false;
    }

}
