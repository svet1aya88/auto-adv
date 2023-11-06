package core.utilities.controls;

import core.browser.DriverSingleton;
import core.utilities.exceptions.TestException;
import core.utilities.waits.Waiter;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

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

    public void displayByJS() {
        JavascriptExecutor js = (JavascriptExecutor) DriverSingleton.getDriverInstance();
        js.executeScript("arguments[0].style.display = 'block';", this.getWrappedElement());
    }

    public void click() {
        Waiter.waitForElement(this.getWrappedElement(), this.getName()).click();
    }

    public void dragAndDrop(WebElement element, WebElement dropZone) {
       new Actions(DriverSingleton.getDriverInstance())
               .clickAndHold(element)
               .moveToElement(dropZone)
               .release()
               .build().perform();
    }

    public void resize(WebElement element) {
        int width = element.getSize().getWidth();
        new Actions(DriverSingleton.getDriverInstance())
                .moveToElement(element, width, 1)
                .clickAndHold()
                .moveByOffset(10, 10)
                .release()
                .build().perform();
    }
}
