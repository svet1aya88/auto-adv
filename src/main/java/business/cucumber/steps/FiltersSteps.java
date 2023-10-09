package business.cucumber.steps;

import business.cucumber.context.Context;
import business.cucumber.context.TestContext;
import business.models.Filter;
import business.pages.filters.FiltersPage;
import business.pages.menu.LeftMenuPanelBlock;
import business.pages.menu.SystemMessageBlock;
import core.Logger;
import core.utilities.exceptions.TestException;
import core.utilities.random.Randomizer;
import core.utilities.waits.Waiter;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltersSteps {

    private TestContext testContext;

    public FiltersSteps(TestContext context) {
        testContext = context;
    }

    @And("User opens Filters page")
    public void openFiltersPage() {
        Logger.info("Open 'Filters' page");
        new LeftMenuPanelBlock().clickFiltersMenu();
    }

    @When("User creates a new filter")
    public void createFilter() {
        Filter newFilter = Filter.builder()
                .name(Randomizer.generateAlphanumericStringWithSpaces())
                .description(Randomizer.generateAlphanumericStringWithSpaces())
                .launchName(Randomizer.generateAlphanumericStringWithSpaces()).build();
        Logger.info("Create new filter:");
        Logger.info("- name: {}", newFilter.getName());
        Logger.info("- description: {}", newFilter.getDescription());
        new FiltersPage()
                .clickAddFilterButton()
                .fillLaunchName(newFilter.getLaunchName())
                .clickSaveButton()
                .fillName(newFilter.getName())
                .clickAddButton();
        testContext.getScenarioContext().setContext(Context.FILTER_NAME, newFilter.getName());
    }

    @Then("Success system message is displayed")
    public void waitForSuccessSystemMessage() {
        Waiter.waitFor(() -> new SystemMessageBlock().isSuccessSystemMessageDisplayed(),
                "Success system message is not displayed after creating new filter!");
    }

    @And("User searches for filter")
    public void searchByName() {
        String filterName = (String) testContext.getScenarioContext().getContext(Context.FILTER_NAME);
        Logger.info("Search filter by name '{}'", filterName);
        new FiltersPage().fillSearchInput(filterName);
    }

    @And("User updates filter name")
    public void updateFilter() {
        String initialName = (String) testContext.getScenarioContext().getContext(Context.FILTER_NAME);
        String newName = Randomizer.generateAlphanumericStringWithSpaces();
        Logger.info("Update filter with:");
        Logger.info("- new name: {}", initialName);
        new FiltersPage()
                .clickFilterEditButton(initialName)
                .fillName(newName)
                .clickUpdateButton();
        testContext.scenarioContext.setContext(Context.FILTER_NAME, newName);
    }

    @Then("Filter is found as search result")
    public void isFilterFound() {
        String filterName = (String) testContext.scenarioContext.getContext(Context.FILTER_NAME);
        boolean isFilterFound;
        try {
            Waiter.waitFor(() -> new FiltersPage().getAllFilterNamesFromTable().stream()
                            .anyMatch(nameFromTable -> nameFromTable.equalsIgnoreCase(filterName)),
                    String.format("Filter names different to '%s' are found!", filterName));
            isFilterFound = true;
        } catch (TestException e) {
            isFilterFound = false;
        }
        assertThat(isFilterFound).as(String.format("Filter '%s' is found", filterName)).isTrue();
    }
}
