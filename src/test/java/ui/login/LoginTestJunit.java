package ui.login;

import business.steps.LoginService;
import core.runner.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTestJunit extends BaseTest {

    @ParameterizedTest
    @DisplayName("Verify failed login")
    @MethodSource("core.utilities.dataprovider.TestDataProvider#getLoginTestData")
    @Execution(ExecutionMode.CONCURRENT)
    void invalidLoginJunit(String username, String password) {
        new LoginService().login(username, password);
        assertThat(new LoginService().isLoginFailed()).as("Login failed").isTrue();
    }
}
