package business.selenide;

import com.codeborne.selenide.SelenideElement;
import core.utilities.waits.WaitTimeout;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;

public class SelenideBasePage {

    protected void waitForPageMarkerElement(SelenideElement pageMarkerElement) {
        pageMarkerElement.shouldBe(visible, Duration.ofSeconds(WaitTimeout.ONE_MINUTE.getSeconds()));
    }
}
