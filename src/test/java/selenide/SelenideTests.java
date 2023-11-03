package selenide;

import business.selenide.SixtCookiesPopup;
import business.selenide.SixtPageMenuBar;
import business.selenide.SixtSearchBlock;
import com.codeborne.selenide.WebDriverRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideTests {

    @BeforeMethod(alwaysRun = true)
    public void openStartPage() {
        open("https://www.sixt.com/");
        new SixtCookiesPopup().accept();
    }

    @AfterMethod(alwaysRun = true)
    public void quitDriver() {
        closeWebDriver();
    }

    @Test(groups = {"selenide"}, description = "Navigate to New York car service article")
    public void sixtNavigateToNewYork() {
        new SixtPageMenuBar()
                .openRideMenu()
                .openNavigationMenu()
                .openNewYork();

        assertThat(WebDriverRunner.url().contains("/ride/new-york/")).isTrue();
    }

    @Test(groups = {"selenide"}, description = "Navigate to New York car service article")
    public void sixtSearchAndSelectACar() {
        new SixtSearchBlock()
                .fillPickUpAndReturn("Budapest Váci Road")
                .showCars()
                .selectCar("Volvo XC90");

        assertThat(WebDriverRunner.url().contains("/offercheckout")).isTrue();
    }
}
