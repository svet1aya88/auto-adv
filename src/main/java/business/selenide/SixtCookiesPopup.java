package business.selenide;

import com.codeborne.selenide.SelenideElement;
import core.Logger;

import static com.codeborne.selenide.Selectors.shadowCss;
import static com.codeborne.selenide.Selenide.$;

public class SixtCookiesPopup {

    public void accept() {
        Logger.info("Accept cookies");
        SelenideElement acceptCookiesButton = $(shadowCss("[data-testid='uc-accept-all-button']", "#usercentrics-root"));
        if (acceptCookiesButton.exists()) {
            acceptCookiesButton.click();
        }
    }
}
