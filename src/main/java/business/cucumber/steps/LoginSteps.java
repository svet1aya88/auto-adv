package business.cucumber.steps;

import business.pages.login.LoginPage;
import business.pages.menu.LeftMenuPanelBlock;
import core.Logger;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginSteps {

    private TestPropertyReader loginProps;

    public LoginSteps() {
        loginProps = new TestPropertyReader(PropertyType.LOGIN);
    }

    @When("User logs into Report Portal with username and password")
    public void login(DataTable dataTable) {
        String loginUrl = loginProps.getProperty(LoginProperty.LOGIN_URL);
        Logger.info("Open Report Portal login page {}", loginUrl);
        new LoginPage().open(loginUrl);
        List<List<String>> credentials = dataTable.asLists(String.class);
        for (List<String> creds : credentials) {
            String username = creds.get(0);
            String password = creds.get(1);
            Logger.info("Login with username/password: {}/{}", username, password);
            new LoginPage()
                    .fillLogin(username)
                    .fillPassword(password)
                    .clickLoginBtn();
        }
    }

    @Given("User logs into Report Portal with username {string} and password {string}")
    public void login(String username, String password) {
        String loginUrl = loginProps.getProperty(LoginProperty.LOGIN_URL);
        Logger.info("Open Report Portal login page {}", loginUrl);
        new LoginPage().open(loginUrl);
        Logger.info("Login with username/password: {}/{}", username, password);
        new LoginPage()
                .fillLogin(username)
                .fillPassword(password)
                .clickLoginBtn();
    }

    @Then("Erroneous field is highlighted")
    public void isLoginFailed() {
        boolean isLoginFailed = new LoginPage().isFormFieldErroneous();
        Logger.info("Login is " + (isLoginFailed ? "failed" : "successful"));
        assertThat(isLoginFailed).as("Login failed").isTrue();
    }

    @Then("User avatar is {booleanValue} in the left-side menu")
    public void isUserIvatarDisplayed(Boolean displayed) {
        boolean isActuallyDisplayed = new LeftMenuPanelBlock().isUserAvatarImgDisplayed();
        Logger.info("User avatar is " + (isActuallyDisplayed ? "displayed" : "not displayed"));
        assertThat(isActuallyDisplayed == displayed).as("User avatar is " + (displayed ? "displayed" : "not displayed"));
    }

    @ParameterType(value = "true|True|TRUE|false|False|FALSE")
    public Boolean booleanValue(String value) {
        return Boolean.valueOf(value);
    }
}
