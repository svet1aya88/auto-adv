package core.utilities.controls;

import core.utilities.exceptions.TestException;
import core.utilities.waits.Waiter;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Getter
public abstract class CustomControl {

    private WebElement wrappedElement;
    private String name;

    protected CustomControl(WebElement wrappedElement, String name) {
        this.wrappedElement = wrappedElement;
        this.name = name;
    }

    public boolean isDisplayed() {
        try {
            Waiter.waitForElement(this.wrappedElement, this.name);
        } catch (TestException e) {
            return false;
        }
        return true;
    }

    public boolean isErroneous() {
        return Waiter.waitForElement(this.wrappedElement, this.name)
                .findElement(By.xpath("./ancestor::div[contains(@class,'inputOutside')]"))
                .getAttribute(ElementAttribute.CLASS.getName()).contains("invalid");
    }
}
