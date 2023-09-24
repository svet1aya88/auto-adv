package business.steps;

import business.pages.login.LoginPage;
import core.Logger;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;

public class LoginService {

    TestPropertyReader loginProps;

    public LoginService() {
        loginProps = new TestPropertyReader(PropertyType.LOGIN);
    }

    public MenuService login(String username, String password) {
        String loginUrl = loginProps.getProperty(LoginProperty.LOGIN_URL);
        Logger.info("Login to {} with username/password: {}/{}", loginUrl, username, password);
        new LoginPage()
                .open(loginUrl)
                .fillLogin(username)
                .fillPassword(password)
                .clickLoginBtn();
        return new MenuService();
    }

    public boolean isLoginFailed() {
        boolean isLoginFailed = new LoginPage().isFormFieldErroneous();
        Logger.info("Login is " + (isLoginFailed ? "failed" : "successful"));
        return isLoginFailed;
    }
}
