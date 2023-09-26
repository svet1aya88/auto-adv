package business.steps;

import business.pages.menu.LeftMenuPanelBlock;
import core.Logger;

public class MenuService {

    public boolean isUserIvatarDisplayed() {
        boolean isDisplayed = new LeftMenuPanelBlock().isUserAvatarImgDisplayed();
        Logger.info("User avatar is " + (isDisplayed ? "displayed" : "not displayed"));
        return isDisplayed;
    }

    public FilterService openFiltersPage() {
        Logger.info("Open 'Filters' page");
        new LeftMenuPanelBlock().clickFiltersMenu();
        return new FilterService();
    }
}
