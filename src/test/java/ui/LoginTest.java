package ui;

import business.pages.dashboards.AllDashboardsPage;
import business.steps.LoginService;
import core.Listener;
import core.runner.BaseTest;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {

    private String username;
    private String password;
    private static final String INVALID_USERNAME = "invalid username";
    private static final String INVALID_PASSWORD = "invalid password";
    private static final String EMPTY_VALUE = " ";

    @BeforeClass(alwaysRun = true)
    public void setup() {
        TestPropertyReader propertyReader = new TestPropertyReader(PropertyType.LOGIN);
        username = propertyReader.getProperty(LoginProperty.USER_DEFAULT_LOGIN);
        password = propertyReader.getProperty(LoginProperty.USER_DEFAULT_PASSWORD);
    }


    @Test(description = "Login as default user", retryAnalyzer = Listener.class)
    public void login() {
        new LoginService().login(username, password);
        assertThat(new AllDashboardsPage().isUserAvatarImgDisplayed()).as("User avatar is displayed").isTrue();
    }

    @Test(description = "Verify failed login", dataProvider = "invalidCreds")
    public void invalidLogin(String username, String password) {
        LoginService loginService = new LoginService().login(username, password);
        assertThat(loginService.isLoginFailed()).as("Login failed").isTrue();
    }

    @DataProvider(parallel = true)
    private Object[][] invalidCreds() {
        return new Object[][]{
                {EMPTY_VALUE, EMPTY_VALUE},
                {EMPTY_VALUE, password},
                {username, EMPTY_VALUE},
                {INVALID_USERNAME, password},
                {username, INVALID_PASSWORD},
                {INVALID_USERNAME, INVALID_PASSWORD}};
    }
}
