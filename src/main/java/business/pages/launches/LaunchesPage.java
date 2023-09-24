package business.pages.launches;

import core.browser.DriverSingleton;
import core.utilities.controls.Button;
import core.utilities.controls.TextInput;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LaunchesPage {

    @FindBy(css = "input[placeholder='Enter name']")
    private WebElement launchNameFilterInput;

    @FindBy(css = "[class*='filterControls__control-button'] button[title='Save']")
    private WebElement saveBtn;

    public LaunchesPage() {
        PageFactory.initElements(DriverSingleton.getDriverInstance(), this);
    }

    public LaunchesPage fillLaunchName(String name) {
        new TextInput(launchNameFilterInput, "Launch name input").fillText(name);
        return this;
    }

    public AddEditFilterPopupBlock clickSaveButton() {
        new Button(saveBtn, "Save button").click();
        return new AddEditFilterPopupBlock();
    }
}
