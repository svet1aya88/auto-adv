package business.selenide;

import core.utilities.controls.ElementAttribute;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SixtSearchBlock {

    public SixtSearchBlock fillPickUpAndReturn(String value) {
        $("#pickupLocation").shouldBe(visible).sendKeys(value);
        $(By.xpath("//div[@id='zensearch_root']//div[text()='Budapest Váci Road']"))
                .shouldBe(visible, Duration.ofSeconds(10)).click();
        return this;
    }

    public SixtCarsOfferPage showCars() {
        $("[data-testid='rent-search-form-cta']>button")
                .shouldNotHave(attribute(ElementAttribute.DISABLED.getName()), Duration.ofSeconds(10)).click();
        return new SixtCarsOfferPage();
    }
}
