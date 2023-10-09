package business.pages.menu;

import business.pages.BasePage;
import business.pages.login.LoginPage;
import core.browser.DriverSingleton;
import core.utilities.controls.Button;
import core.utilities.controls.Image;
import core.utilities.controls.Link;
import core.utilities.waits.Waiter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeftMenuPanelBlock extends BasePage {

    @FindBy(css = "img[class*='userBlock__avatar']")
    private WebElement userAvatarImg;

    @FindBy(css = "a[href*='/filters']")
    private WebElement filtersMenuBtn;

    @FindBy(css = "div[class*='userBlock__menu']:not([class*='userBlock__menu-item']):not([class*='userBlock__menu-arrow'])")
    private WebElement userMenuPopup;

    @FindBy(css = "div[class*='userBlock__menu-item']")
    private WebElement logoutLink;

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

    public LeftMenuPanelBlock clickAvatarImg() {
        new Image(userAvatarImg, "User avatar").click();
        Waiter.waitForElement(userMenuPopup, "User menu popup");
        return new LeftMenuPanelBlock();
    }

    public LoginPage clickLogout() {
        new Link(logoutLink, "Logout link").click();
        return new LoginPage();
    }
}
