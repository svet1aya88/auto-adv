package business.pages;

import core.browser.DriverSingleton;
import core.utilities.waits.Waiter;

public abstract class BasePage {

    protected BasePage() {
        Waiter.waitForAjax();
        Waiter.waitForPageLoad();
    }

    private void open(String url) {
        DriverSingleton.getDriverInstance().get(url);
    }

    protected <T> T navigateTo(Class<T> pageClass, String url) {
        open(url);
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
