package business.cucumber.steps;

import business.pages.menu.LeftMenuPanelBlock;
import core.Logger;
import io.cucumber.java.en.Then;

public class FiltersSteps {

    @Then("User opens Filters page")
    public void openFiltersPage() {
        Logger.info("Open 'Filters' page");
        new LeftMenuPanelBlock().clickFiltersMenu();
    }
}
