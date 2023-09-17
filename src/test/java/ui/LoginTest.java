package ui;

import business.pages.dashboards.AllDashboardsPage;
import business.pages.login.LoginPage;
import core.Listener;
import core.runner.BaseTest;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest extends BaseTest {

    private String login;
    private String password;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        TestPropertyReader propertyReader = new TestPropertyReader(PropertyType.LOGIN);
        login = propertyReader.getProperty(LoginProperty.USER_DEFAULT_LOGIN);
        password = propertyReader.getProperty(LoginProperty.USER_DEFAULT_PASSWORD);
    }

    @Test(description = "Login as default user", retryAnalyzer = Listener.class)
    public void login() {
        new LoginPage()
                .open()
                .fillLogin(login)
                .fillPassword(password)
                .clickLoginBtn();
        assertThat(new AllDashboardsPage().isUserAvatarImgDisplayed()).as("User avatar is displayed").isTrue();
    }
}
