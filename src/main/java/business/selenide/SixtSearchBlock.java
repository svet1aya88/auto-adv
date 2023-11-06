package business.selenide;

import com.codeborne.selenide.SelenideElement;
import core.Logger;
import core.utilities.controls.ElementAttribute;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SixtSearchBlock {

    public SixtSearchBlock fillPickUpAndReturn(String value) {
        $("#pickupLocation").shouldBe(visible).sendKeys(value);
        $(By.xpath("//div[@id='zensearch_root']//div[text()='Budapest Váci Road']"))
                .shouldBe(visible, Duration.ofSeconds(10)).click();
        Logger.info("Fill pickup and return point as '{}'", value);
        return this;
    }

    public SixtCarsOfferPage showCars() {
        SelenideElement element = $("[data-testid='rent-search-form-cta']>button")
                .shouldNotHave(attribute(ElementAttribute.DISABLED.getName()), Duration.ofSeconds(10));
//        resizeElement(element);
        element.click();
        Logger.info("Show cars");
        return new SixtCarsOfferPage();
    }

    private void resizeElement(SelenideElement element) {
        int width = element.getWrappedElement().getSize().getWidth();
        new Actions(getWebDriver())
                .moveToElement(element.getWrappedElement(), width, 1)
                .clickAndHold()
                .moveByOffset(10, 10)
                .release()
                .build().perform();
    }
}
