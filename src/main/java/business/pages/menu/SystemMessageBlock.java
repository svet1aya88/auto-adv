package business.pages.menu;

import core.browser.DriverSingleton;
import core.utilities.controls.ElementAttribute;
import core.utilities.waits.Waiter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SystemMessageBlock {

    @FindBy(css = "[class*='notificationItem__message']")
    private WebElement messageText;

    public SystemMessageBlock() {
        PageFactory.initElements(DriverSingleton.getDriverInstance(), this);
    }

    public boolean isSuccessSystemMessageDisplayed() {
        return Waiter.waitForElement(messageText, "System message")
                .getAttribute(ElementAttribute.CLASS.getName()).contains("success");
    }
}
