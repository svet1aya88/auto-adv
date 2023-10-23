package business.models.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterType {

    LAUNCH("launch");

    private final String value;
}
