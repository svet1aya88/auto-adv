package business.pages.dashboards;

import business.pages.BasePage;
import core.browser.DriverSingleton;
import core.utilities.controls.CustomControl;
import core.utilities.controls.Image;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AllDashboardsPage extends BasePage {

    @FindBy(css = "img[class*='userBlock__avatar']")
    private WebElement userAvatarImg;

    public AllDashboardsPage() {
        super();
        PageFactory.initElements(DriverSingleton.getDriverInstance(), this);
    }

    public boolean isUserAvatarImgDisplayed() {
        return new Image(userAvatarImg, "User avatar").isDisplayed();
    }
}
