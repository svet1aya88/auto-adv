package business.pages;

import core.browser.DriverSingleton;
import core.utilities.waits.Waiter;

public abstract class BasePage {

    protected BasePage() {
        Waiter.waitForAjax();
        Waiter.waitForPageLoad();
    }

    protected void open(String url) {
        DriverSingleton.getDriverInstance().get(url);
    }
}
