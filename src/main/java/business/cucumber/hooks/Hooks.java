package business.cucumber.hooks;

import core.Logger;
import core.browser.DriverSingleton;
import io.cucumber.java.After;

public class Hooks {

    @After()
    public void afterScenario() {
        Logger.debug("Quit driver");
        DriverSingleton.quit();
    }
}
