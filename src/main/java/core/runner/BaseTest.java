package core.runner;

import core.Logger;
import core.browser.DriverSingleton;
import org.junit.jupiter.api.AfterEach;
import org.testng.annotations.AfterMethod;

public abstract class BaseTest {

    @AfterMethod(alwaysRun = true)
    public void quit() {
        Logger.debug("Quit driver");
        DriverSingleton.quit();
    }

    @AfterEach
    public void quitDriver() {
        Logger.debug("Quit driver");
        DriverSingleton.quit();
    }
}
