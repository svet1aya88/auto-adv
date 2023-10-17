package business.models.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonAutoDetect
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterCondition {

    @JsonProperty("condition")
    private String condition;
    @JsonProperty("filteringField")
    private String filteringField;
    @JsonProperty("value")
    private String value;
}
