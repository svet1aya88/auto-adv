package business.selenide;

import com.codeborne.selenide.SelenideElement;
import core.Logger;
import core.utilities.controls.JavaScriptActions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SixtPageMenuBar {

    public SixtPageMenuBar openNavigationMenu() {
        SelenideElement menu = $("[class*='Burgerstyled__Burger']").should(exist);
        JavaScriptActions.clickWithJS(menu);
        $("[class*='Collapsiblestyled'] a").shouldBe(visible, Duration.ofSeconds(10));
        Logger.info("Open navigation menu");
        return new SixtPageMenuBar();
    }

    public void openNewYork() {
        $("li[class*='Navigationstyled']>a[href*='/ride/new-york/']").shouldBe(visible).click();
        $("div>h1").shouldHave(text("New York City Car Service with SIXT"));
        Logger.info("Open 'New York' menu");
    }

    public SixtPageMenuBar openRideMenu() {
        $("a[href*='ride']").shouldBe(visible).click();
        Logger.info("Open main menu 'Ride'");
        return new SixtPageMenuBar();
    }
}
