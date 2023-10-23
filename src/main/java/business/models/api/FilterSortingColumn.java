package business.models.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterSortingColumn {

    START_TIME("startTime");

    private final String value;
}
