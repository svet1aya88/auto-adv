package core.utilities.controls;

import core.utilities.waits.Waiter;
import lombok.Getter;
import org.openqa.selenium.WebElement;

public class TextInput extends CustomControl {

    public TextInput(WebElement element, String name) {
        super(element, name);
    }

    public void fillText(String text) {
        Waiter.waitForElement(super.getWrappedElement(), super.getName());
        super.getWrappedElement().sendKeys(text);
    }
}
