package core;

import com.google.common.io.Files;
import core.browser.DriverSingleton;
import core.utilities.exceptions.PropertyException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;

public class Listener extends TestListenerAdapter implements IInvokedMethodListener, ITestListener, IRetryAnalyzer {

    boolean testSuccess = true;

    int count = 1;
    int maxRetryCount = 2;

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && !testSuccess) {
            testResult.setStatus(ITestResult.FAILURE);
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
        captureScreenshot(tr);
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
        captureScreenshot(tr);
    }

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetryCount) {
            count++;
            return true;
        }
        return false;
    }

    private byte[] captureScreenshot(final ITestResult tr) {
        File f = null;
        try {
            f = ((TakesScreenshot) DriverSingleton.getDriverInstance()).getScreenshotAs(OutputType.FILE);
            File outputDir = new File(tr.getTestContext().getOutputDirectory());
            File saved = new File(outputDir.getParent(), tr.getName() + ".png");
            try {
                FileUtils.copyFile(f, saved);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Reporter.log("<img src=\"" + saved.getName() + "\">", true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return Files.toByteArray(f);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException | NoSuchSessionException e) {
            e.printStackTrace();
            throw new PropertyException("Cannot capture a screenshot!");
        }
        return new byte[0];
    }
}
