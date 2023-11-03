package business.selenide;

import com.codeborne.selenide.SelenideElement;
import core.utilities.controls.JavaScriptActions;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SixtPageMenuBar {

    public SixtPageMenuBar openNavigationMenu() {
        SelenideElement menu = $("[class*='Burgerstyled__Burger']").should(exist);
        JavaScriptActions.clickWithJS(menu);
        $("[class*='item-inner']>a").shouldBe(visible);
        return new SixtPageMenuBar();
    }

    public void openNewYork() {
        $("a[href='/ride/new-york/']").shouldBe(visible).click();
        $("div>h1").shouldHave(text("New York City Car Service with SIXT"));
    }

    public SixtPageMenuBar openRideMenu() {
        $("a[href*='ride']").shouldBe(visible).click();
        return new SixtPageMenuBar();
    }
}
