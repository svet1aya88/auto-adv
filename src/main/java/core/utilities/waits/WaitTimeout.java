package core.utilities.waits;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WaitTimeout {

    ONE_MINUTE(60),
    FIVE_MINUTES(300);
    private final int seconds;
}
