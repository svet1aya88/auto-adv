package business.pages.login;

import business.pages.BasePage;
import core.Logger;
import core.browser.DriverSingleton;
import core.utilities.controls.Button;
import core.utilities.controls.TextInput;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    private static final String LOGIN_URL = "http://localhost:8080/ui/";

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
        Logger.info("Fill login '{}'", value);
        return this;
    }

    public LoginPage fillPassword(String value) {
        new TextInput(passwordInput, "Password input").fillText(value);
        Logger.info("Fill password '{}'", value);
        return this;
    }

    public void clickLoginBtn() {
        new Button(loginBtn, "Login button").click();
        Logger.info("Click login button");
    }

    public LoginPage open() {
        super.open(LOGIN_URL);
        Logger.info("Open Report Portal login page");
        return new LoginPage();
    }
}
