package ui;

import business.steps.LoginService;
import core.runner.BaseTest;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTestJunit extends BaseTest {

    private static String validUsername;
    private static String validPassword;
    private static final String INVALID_USERNAME = "invalid username";
    private static final String INVALID_PASSWORD = "invalid password";
    private static final String EMPTY_VALUE = " ";

    @BeforeAll
    static void setup() {
        TestPropertyReader propertyReader = new TestPropertyReader(PropertyType.LOGIN);
        validPassword = propertyReader.getProperty(LoginProperty.USER_DEFAULT_PASSWORD);
        validUsername = propertyReader.getProperty(LoginProperty.USER_DEFAULT_LOGIN);
    }

    @ParameterizedTest
    @DisplayName("Verify failed login")
    @MethodSource("invalidCreds")
    void invalidLogin(String username, String password) {
        LoginService loginService = new LoginService().login(username, password);
        assertThat(loginService.isLoginFailed()).as("Login failed").isTrue();
    }

    static Stream<Arguments> invalidCreds() {
        return Stream.of(
                Arguments.of(EMPTY_VALUE, EMPTY_VALUE),
                Arguments.of(EMPTY_VALUE, validPassword),
                Arguments.of(validUsername, EMPTY_VALUE),
                Arguments.of(INVALID_USERNAME, validPassword),
                Arguments.of(validUsername, INVALID_PASSWORD),
                Arguments.of(INVALID_USERNAME, INVALID_PASSWORD)
        );
    }
}
