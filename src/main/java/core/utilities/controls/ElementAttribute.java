package core.utilities.controls;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ElementAttribute {

    CLASS("class"),
    ALT("alt"),
    DISABLED("disabled");
    private final String name;
}
