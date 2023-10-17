package core.utilities.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiProperty implements Property {

    OAUTH_TOKEN("oauth.token"),
    FILTER_URI("filter.uri");

    private final String key;
}
