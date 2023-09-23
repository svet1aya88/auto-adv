package core.utilities.waits;

import core.Logger;
import core.browser.DriverSingleton;
import core.utilities.exceptions.TestException;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.awaitility.core.ConditionTimeoutException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class Waiter {

    private Waiter() {
    }

    private static void jsWait(JavascriptWaits wait) {
        WebDriver driverInstance = DriverSingleton.getDriverInstance();
        try {
            Awaitility.await()
                    .atMost(Duration.ofSeconds(WaitTimeout.ONE_MINUTE.getSeconds()))
                    .pollInSameThread().pollInterval(Durations.ONE_SECOND)
                    .until(() -> {
                        JavascriptExecutor js = (JavascriptExecutor) driverInstance;
                        return (Boolean) js.executeScript(wait.getJsCode());
                    });
        } catch (Exception e) {
            Logger.warn("Error during JS waiting: {}", e.getMessage());
        }
    }

    public static void waitForAjax() {
        jsWait(JavascriptWaits.JQUERY_AJAX);
    }

    public static void waitForPageLoad() {
        jsWait(JavascriptWaits.PAGE_LOAD);
    }

    public static WebElement waitForElement(WebElement element, String elementName) throws TestException {
        try {
            Awaitility.await().alias(String.format("Wait for '%s' web element to be displayed", elementName))
                    .atMost(Duration.ofSeconds(WaitTimeout.ONE_MINUTE.getSeconds()))
                    .pollInSameThread().pollInterval(Durations.ONE_SECOND)
                    .ignoreExceptions()
                    .until(element::isDisplayed);
        } catch (ConditionTimeoutException e) {
            throw new TestException("'{}' web element is not displayed!", elementName);
        }
        return element;
    }
}
