package business.pages.launches;

import core.browser.DriverSingleton;
import core.utilities.controls.Button;
import core.utilities.controls.TextInput;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddEditFilterPopupBlock {

    @FindBy(css = "[class*='modalField'] input")
    private WebElement nameInput;

    @FindBy(xpath = "//*[contains(@class,'modalFooter')]/button[text()='Add']")
    private WebElement addBtn;

    @FindBy(xpath = "//*[contains(@class,'modalFooter')]/button[text()='Update']")
    private WebElement updateBtn;

    public AddEditFilterPopupBlock() {
        PageFactory.initElements(DriverSingleton.getDriverInstance(), this);
    }

    public AddEditFilterPopupBlock fillName(String name) {
        new TextInput(nameInput, "Name input").fillText(name);
        return this;
    }

    public void clickAddButton() {
        new Button(addBtn, "Add button").click();
    }

    public void clickUpdateButton() {
        new Button(updateBtn, "Update button").click();
    }
}
