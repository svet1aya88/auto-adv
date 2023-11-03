package business.selenide;

import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;

public class SixtCookiesPopup {

    public void accept() {
        $(shadowCss("[data-testid='uc-accept-all-button']", "#usercentrics-root")).click();
//        $("#usercentrics-root").getShadowRoot().findElement(By.cssSelector("[data-testid='uc-accept-all-button']")).click();
    }
}
