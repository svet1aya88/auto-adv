package ui.filters;

import business.models.Filter;
import business.steps.FilterService;
import business.steps.LoginService;
import business.steps.MenuService;
import core.runner.BaseTest;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import core.utilities.random.Randomizer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltersTests extends BaseTest {

    private static final int FILTER_NAME_MIN_LENGTH = 3;
    private static final int FILTER_NAME_MAX_LENGTH = 128;

    private TestPropertyReader propertyReader;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        propertyReader = new TestPropertyReader(PropertyType.LOGIN);
    }

    @Test(description = "Create filter", dataProvider = "validFilterName", groups = {"filters"})
    public void createFilter(String filterName) {
        Filter newFilter = Filter.builder()
                .name(filterName)
                .description(Randomizer.generateAlphanumericStringWithSpaces())
                .launchName(Randomizer.generateAlphanumericStringWithSpaces()).build();

        FilterService filterService = new LoginService()
                .login(propertyReader.getProperty(LoginProperty.USER_DEFAULT_LOGIN),
                        propertyReader.getProperty(LoginProperty.USER_DEFAULT_PASSWORD))
                .openFiltersPage()
                .createFilter(newFilter);
        new MenuService().openFiltersPage();
        filterService.searchByName(newFilter.getName());

        assertThat(filterService.isFilterFound(newFilter.getName()))
                .as(String.format("Filter with name '%s' is found", newFilter.getName()))
                .isTrue();
    }

    @Test(description = "Update filter name", dataProvider = "validFilterName", groups = {"filters"})
    public void updateFilterName(String newFilterName) {
        Filter filterForUpdate = Filter.builder()
                .name(Randomizer.generateAlphanumericStringWithSpaces())
                .description(Randomizer.generateAlphanumericStringWithSpaces())
                .launchName(Randomizer.generateAlphanumericStringWithSpaces()).build();

        FilterService filterService = new LoginService()
                .login(propertyReader.getProperty(LoginProperty.USER_DEFAULT_LOGIN),
                        propertyReader.getProperty(LoginProperty.USER_DEFAULT_PASSWORD))
                .openFiltersPage()
                .createFilter(filterForUpdate);
        new MenuService().openFiltersPage();
        filterService
                .searchByName(filterForUpdate.getName())
                .updateFilter(filterForUpdate.getName(), newFilterName)
                .searchByName(newFilterName);

        assertThat(filterService.isFilterFound(newFilterName))
                .as(String.format("Filter with updated name '%s' is found", filterForUpdate.getName()))
                .isTrue();
    }

    @DataProvider(parallel = true)
    private Object[][] validFilterName() {
        return new Object[][]{
//                {Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MIN_LENGTH)},
//                {Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MAX_LENGTH)},
//                {Randomizer.generateAlphanumericStringWithSpaces(FILTER_NAME_MAX_LENGTH, FILTER_NAME_MAX_LENGTH)},
//                {Randomizer.generateStringWithSpecialSymbols(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MAX_LENGTH)},
                {Randomizer.generateMultiLanguageString(FILTER_NAME_MIN_LENGTH, FILTER_NAME_MAX_LENGTH)}};
    }
}
