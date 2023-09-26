package business.pages.login;

import business.pages.BasePage;
import core.browser.DriverSingleton;
import core.utilities.controls.Button;
import core.utilities.controls.TextInput;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(css = "[name='login']")
    private WebElement loginInput;

    @FindBy(css = "[name='password'")
    private WebElement passwordInput;

    @FindBy(css = "[class*='login-button'] button")
    private WebElement loginBtn;

    public LoginPage() {
        super();
        PageFactory.initElements(DriverSingleton.getDriverInstance(), this);
    }

    public LoginPage fillLogin(String value) {
        new TextInput(loginInput, "Login input").fillText(value);
        return this;
    }

    public LoginPage fillPassword(String value) {
        new TextInput(passwordInput, "Password input").fillText(value);
        return this;
    }

    public void clickLoginBtn() {
        new Button(loginBtn, "Login button").click();
    }

    public LoginPage open(String url) {
        return super.navigateTo(LoginPage.class, url);
    }

    public boolean isFormFieldErroneous() {
        return new TextInput(loginInput, "Login input").isErroneous() ||
                new TextInput(passwordInput, "Password input").isErroneous();
    }
}
