package business.selenide;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import core.utilities.controls.ElementAttribute;
import core.utilities.controls.JavaScriptActions;
import core.utilities.waits.Waiter;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$$;

public class SixtCarsOfferPage {

    public void selectCar(String name) {
        SelenideElement carImage = $$("img[src*='global/user_upload']")
                .find(attribute(ElementAttribute.ALT.getName(), name))
                .should(exist);
        JavaScriptActions.scrollIntoView(carImage).click();
        Waiter.waitFor(() -> WebDriverRunner.url().contains("/offercheckout"), "Failed to load checkout!");
    }
}
