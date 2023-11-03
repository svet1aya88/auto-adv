package business.steps;

import business.models.ui.Filter;
import business.pages.filters.FiltersPage;
import business.pages.menu.SystemMessageBlock;
import core.Logger;
import core.utilities.exceptions.TestException;
import core.utilities.waits.Waiter;

import java.util.List;

public class FilterService {

    public List<String> getAllFilterNames() {
        List<String> allFilterNames = new FiltersPage().getAllFilterNamesFromTable();
        Logger.info("Filter names: {}", allFilterNames);
        return allFilterNames;
    }

    public FilterService createFilter(Filter newFilter) {
        Logger.info("Create new filter:");
        Logger.info("- name: {}", newFilter.getName());
        Logger.info("- description: {}", newFilter.getDescription());
        new FiltersPage()
                .clickAddFilterButton()
                .fillLaunchName(newFilter.getLaunchName())
                .clickSaveButton()
                .fillName(newFilter.getName())
                .clickAddButton();
        waitForSuccessSystemMessage();
        return this;
    }

    public FilterService searchByName(String name) {
        Logger.info("Search filter by name '{}'", name);
        new FiltersPage().fillSearchInput(name);
        return this;
    }

    public boolean isFilterFound(String name) {
        try {
            Waiter.waitFor(() -> getAllFilterNames().stream()
                            .anyMatch(nameFromTable -> nameFromTable.equalsIgnoreCase(name)),
                    String.format("Filter names different to '%s' are found!", name));
        } catch (TestException e) {
            return false;
        }
        return true;
    }

    public FilterService updateFilter(String initialName, String newName) {
        Logger.info("Update filter with:");
        Logger.info("- new name: {}", initialName);
        new FiltersPage()
                .clickFilterEditButton(initialName)
                .fillName(newName)
                .clickUpdateButton();
        waitForSuccessSystemMessage();
        return this;
    }

    private void waitForSuccessSystemMessage() {
        Waiter.waitFor(() -> new SystemMessageBlock().isSuccessSystemMessageDisplayed(),
                "Success system message is not displayed after creating new filter!");
    }
}
