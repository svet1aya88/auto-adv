package core.utilities.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropertyStage {

    QA("qa"), PRD("prd");

    private final String name;
}
