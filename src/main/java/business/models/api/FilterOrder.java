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
public class FilterOrder {

    @JsonProperty("isAsc")
    private boolean isAsc;
    @JsonProperty("sortingColumn")
    private String sortingColumn;
}
