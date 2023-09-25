package core.utilities.controls;

import core.utilities.waits.Waiter;
import org.openqa.selenium.WebElement;

public class TextInput extends CustomControl {

    public TextInput(WebElement element, String name) {
        super(element, name);
    }

    public void fillText(String text) {
        WebElement input = Waiter.waitForElement(super.getWrappedElement(), super.getName());
        input.clear();
        input.sendKeys(text);
    }
}
