package business.selenide;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SelenideLoginPage extends SelenideBasePage {

    private SelenideElement loginInput = $(By.cssSelector("[name='login']"));

    public SelenideLoginPage fillLogin(String value) {
        loginInput.shouldBe(visible).sendKeys(value);
        return this;
    }

    public SelenideLoginPage fillPassword(String value) {
        $(By.cssSelector("[name='password']")).shouldBe(visible).sendKeys(value);
        return this;
    }

    public void clickLoginBtn() {
        $(By.cssSelector("[class*='login-button'] button")).shouldBe(visible).click();
    }

    public SelenideLoginPage waitForLoad() {
        super.waitForPageMarkerElement(loginInput);
        return this;
    }
}
