package business.models.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilteringFieldType {

    COMPOSITE_ATTRIBUTE("compositeAttribute");

    private final String value;
}
