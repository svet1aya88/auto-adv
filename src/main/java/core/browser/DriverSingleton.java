package core.browser;

import core.Logger;
import core.utilities.exceptions.PropertyException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSingleton {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final String BROWSER_PROPERTY = "browser";
    private static final String DRIVER_FIREFOX = "webdriver.gecko.driver";
    private static final String DRIVER_CHROME = "webdriver.chrome.driver";

    private DriverSingleton() {
    }

    public enum DriverType {
        CHROME, FIREFOX
    }

    public static WebDriver getDriverInstance() {
        if (driver.get() == null) {
            driver.set(createDriver());
        }
        return driver.get();
    }

    private static WebDriver createDriver() {
        WebDriver driver = null;
        switch (getDriverType()) {
            case FIREFOX:
                System.setProperty(DRIVER_FIREFOX, DriverPropertyHandler.getFirefoxDriverPath());
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                driver = new FirefoxDriver();
                break;
            case CHROME:
                System.setProperty(DRIVER_CHROME, DriverPropertyHandler.getChromeDriverPath());
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static void quit() {
        try {
            if (driver.get() != null) {
                driver.get().quit();
                driver.remove();
            }
        } catch (NoSuchSessionException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDriverInitialized() {
        boolean isDriverInitialized = (DriverSingleton.getDriverInstance() != null);
        Logger.debug("VM_ERROR_CHECK isDriverInitialized() is " +
                        (isDriverInitialized ? "TRUE, driver exist" : "FALSE - no driver") + " for threadId: '{}'",
                Thread.currentThread().getId());
        return isDriverInitialized;
    }

    private static DriverType getDriverType() {
        String browserProperty = System.getProperty("browser");
        if (browserProperty == null) {
            browserProperty = "firefox";
        }
        switch (browserProperty) {
            case "chrome":
                return DriverType.CHROME;
            case "firefox":
                return DriverType.FIREFOX;
            default:
                throw new PropertyException("Browser type '{}' is not defined in properties file", browserProperty);
        }
    }
}
