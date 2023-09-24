package core.utilities.controls;

import core.utilities.waits.Waiter;
import org.openqa.selenium.WebElement;

public class Link extends CustomControl {

    public Link(WebElement wrappedElement, String name) {
        super(wrappedElement, name);
    }

    public void click() {
        Waiter.waitForElement(super.getWrappedElement(), super.getName()).click();
    }
}
