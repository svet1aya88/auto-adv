package core.browser;

import core.Logger;
import core.utilities.exceptions.PropertyException;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSingleton {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();
    private static final String BROWSER_PROPERTY = "browser";
    private static final String DRIVER_FIREFOX = "webdriver.gecko.driver";
    private static final String DRIVER_CHROME = "webdriver.chrome.driver";

    private DriverSingleton() {
    }

    public enum DriverType {
        CHROME, FIREFOX
    }

    public static WebDriver getDriverInstance() {
        if (DRIVER.get() == null) {
            DRIVER.set(createDriver());
        }
        return DRIVER.get();
    }

    private static WebDriver createDriver() {
        WebDriver driver;
        switch (getDriverType()) {
            case FIREFOX -> {
                System.setProperty(DRIVER_FIREFOX, DriverPropertyHandler.getFirefoxDriverPath());
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_PROFILE, "/dev/null");
                driver = new FirefoxDriver();
            }
            case CHROME -> {
                System.setProperty(DRIVER_CHROME, DriverPropertyHandler.getChromeDriverPath());
                driver = new ChromeDriver();
            }
            default -> throw new PropertyException("Unsupported driver type!");
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static void quit() {
        try {
            if (DRIVER.get() != null) {
                DRIVER.get().quit();
                DRIVER.remove();
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
        String browserProperty = System.getProperty(BROWSER_PROPERTY);
        if (browserProperty == null) {
            browserProperty = "firefox";
        }
        return switch (browserProperty) {
            case "chrome" -> DriverType.CHROME;
            case "firefox" -> DriverType.FIREFOX;
            default ->
                    throw new PropertyException("Browser type '{}' is not defined in properties file", browserProperty);
        };
    }
}
