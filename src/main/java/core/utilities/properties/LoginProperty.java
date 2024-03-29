package core.utilities.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginProperty implements Property {

    LOGIN_URL("login.url"),
    USER_DEFAULT_LOGIN("user.default.login"),
    USER_DEFAULT_PASSWORD("user.default.password");
    private final String key;
}
