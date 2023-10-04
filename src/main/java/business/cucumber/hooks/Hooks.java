package business.cucumber.hooks;

import business.pages.menu.LeftMenuPanelBlock;
import core.Logger;
import core.browser.DriverSingleton;
import io.cucumber.java.After;

public class Hooks {

    @After()
    public void afterScenario() {
        Logger.info("Logout");
        new LeftMenuPanelBlock().clickAvatarImg().clickLogout();
        Logger.debug("Quit driver");
        DriverSingleton.quit();
    }
}
