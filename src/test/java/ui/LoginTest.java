package ui;

import business.pages.dashboards.AllDashboardsPage;
import business.pages.login.LoginPage;
import core.browser.Browser;
import core.browser.DriverSingleton;
import core.runner.BaseTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

public class LoginTest extends BaseTest {

    @Test()
    public void login() {
        new LoginPage()
                .open()
                .fillLogin("default")
                .fillPassword("1q2w3e")
                .clickLoginBtn();
        assertThat(new AllDashboardsPage().isUserAvatarImgDisplayed()).as("User avatar is displayed").isTrue();
    }
}
