package business.selenide;

import core.utilities.controls.ElementAttribute;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SelenideLaunchesPage extends SelenideBasePage {

    public SelenideLaunchesPage fillLaunchName(String name) {
        $(By.cssSelector("input[placeholder='Enter name']")).shouldBe(visible).sendKeys(name);
        return this;
    }

    public SelenideLaunchesPage clickSaveButton() {
        $(By.cssSelector("[class*='filterControls__control-button'] button[title='Save']")).shouldBe(visible).click();
        return new SelenideLaunchesPage();
    }

    public SelenideLaunchesPage fillFilterName(String name) {
        $(By.cssSelector("[class*='modalField'] input")).shouldBe(visible).sendKeys(name);
        return this;
    }

    public SelenideLaunchesPage clickAddButton() {
        $(By.xpath("//*[contains(@class,'modalFooter')]/button[text()='Add']")).shouldBe(visible).click();
        return new SelenideLaunchesPage();
    }

    public void waitForSystemMessage() {
        $(By.cssSelector("[class*='notificationItem__message']"))
                .shouldHave(attributeMatching(ElementAttribute.CLASS.getName(), ".+success.+"));
    }
}
