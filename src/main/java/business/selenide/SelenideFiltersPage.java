package business.selenide;

import com.codeborne.selenide.SelenideElement;
import core.utilities.controls.ElementAttribute;
import core.utilities.waits.Waiter;
import org.openqa.selenium.By;

import java.util.Objects;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SelenideFiltersPage extends SelenideBasePage {

    public SelenideFiltersPage open() {
        $(By.cssSelector("a[href*='/filters']")).shouldBe(visible).click();
        return new SelenideFiltersPage();
    }

    public SelenideLaunchesPage clickAddFilterButton() {
        $(By.cssSelector("[class*='filterPageToolbar'] button")).shouldBe(visible).click();
        return new SelenideLaunchesPage();
    }

    public SelenideFiltersPage fillSearchInput(String name) {
        $(By.cssSelector("input[class*='inputSearch__input']")).shouldBe(visible).sendKeys(name);
        return this;
    }

    public SelenideFiltersPage disableDisplayOnLaunchesForFilter(String filterName) {
        SelenideElement switchElement = getDisplayOnLaunchesSwitchPerFilter(filterName);
        switchElement.click();
        Waiter.waitFor(() -> !isFilterEnabledOnLaunches(filterName),
                String.format("Filter '%s' is not disabled on launches!", filterName));
        return this;
    }

    public boolean isFilterEnabledOnLaunches(String filterName) {
        return Objects.requireNonNull(getDisplayOnLaunchesSwitchPerFilter(filterName)
                .$(By.xpath("./span[contains(@class,'slider')]"))
                .attr(ElementAttribute.CLASS.getName())).contains("inputSwitcher__on");
    }

    private SelenideElement getDisplayOnLaunchesSwitchPerFilter(String filterName) {
        return $$(By.cssSelector("a[class*='filterName__name']")).find(text(filterName))
                .$(By.xpath("./ancestor::div[contains(@class,'gridRow__grid-row-wrapper')]//label[contains(@class,'inputSwitcher')]"));
    }
}
