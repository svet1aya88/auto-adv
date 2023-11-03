package business.models.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterConditionType {

    HAS("has");

    private final String value;
}
