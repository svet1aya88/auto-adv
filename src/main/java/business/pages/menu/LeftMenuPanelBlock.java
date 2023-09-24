package business.pages.menu;

import business.pages.BasePage;
import core.browser.DriverSingleton;
import core.utilities.controls.Button;
import core.utilities.controls.Image;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeftMenuPanelBlock extends BasePage {

    @FindBy(css = "img[class*='userBlock__avatar']")
    private WebElement userAvatarImg;

    @FindBy(css = "a[href*='/filters']")
    private WebElement filtersMenuBtn;

    public LeftMenuPanelBlock() {
        super();
        PageFactory.initElements(DriverSingleton.getDriverInstance(), this);
    }

    public boolean isUserAvatarImgDisplayed() {
        return new Image(userAvatarImg, "User avatar").isDisplayed();
    }

    public void clickFiltersMenu() {
        new Button(filtersMenuBtn, "Filters menu").click();
    }
}
