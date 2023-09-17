package core.utilities.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyType {

    LOGIN("login.properties");
    private final String fileName;
}
