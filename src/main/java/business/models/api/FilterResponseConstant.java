package business.models.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterResponseConstant {

    ERROR_CODE("errorCode"),
    MESSAGE("message");

    private final String value;
}
