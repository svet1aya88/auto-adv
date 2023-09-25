package ui.login;

import business.steps.LoginService;
import business.steps.MenuService;
import core.Listener;
import core.runner.BaseTest;
import core.utilities.dataprovider.DataType;
import core.utilities.dataprovider.TestDataProvider;
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

    @BeforeClass(alwaysRun = true)
    public void setup() {
        TestPropertyReader propertyReader = new TestPropertyReader(PropertyType.LOGIN);
        username = propertyReader.getProperty(LoginProperty.USER_DEFAULT_LOGIN);
        password = propertyReader.getProperty(LoginProperty.USER_DEFAULT_PASSWORD);
    }

    @Test(description = "Login as default user", retryAnalyzer = Listener.class, groups = {"login"})
    public void login() {
        new LoginService().login(username, password);
        assertThat(new MenuService().isUserIvatarDisplayed()).as("User avatar is displayed").isTrue();
    }

    @Test(description = "Verify failed login", dataProvider = "invalidCreds", groups = {"login"})
    public void invalidLogin(String username, String password) {
        new LoginService().login(username, password);
        assertThat(new LoginService().isLoginFailed()).as("Login failed").isTrue();
    }

    @DataProvider(parallel = true)
    private Object[][] invalidCreds() {
        return TestDataProvider.getData(DataType.LOGIN_CREDS);
    }
}
