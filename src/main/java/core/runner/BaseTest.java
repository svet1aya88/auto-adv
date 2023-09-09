package core.runner;

import core.Listener;
import core.Logger;
import core.browser.DriverSingleton;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;

@Listeners(Listener.class)
public abstract class BaseTest {

    @AfterMethod(alwaysRun = true)
    public void quit() {
        Logger.debug("Quit driver");
        DriverSingleton.quit();
    }
}
