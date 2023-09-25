package ui.filters;

import business.models.Filter;
import business.steps.FilterService;
import business.steps.LoginService;
import business.steps.MenuService;
import core.utilities.properties.LoginProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import core.utilities.random.Randomizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.testng.annotations.BeforeClass;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltersTestsJunit {

    private TestPropertyReader propertyReader;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        propertyReader = new TestPropertyReader(PropertyType.LOGIN);
    }

    @ParameterizedTest
    @DisplayName("Create filter")
//    @MethodSource("core.utilities.dataprovider.TestDataProvider#getDataJunit")
    @Execution(ExecutionMode.CONCURRENT)
    void createFilter(String filterName) {
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

    @ParameterizedTest
    @DisplayName("Update filter name")
//    @MethodSource("core.utilities.dataprovider.TestDataProvider#getDataJunit")
    @Execution(ExecutionMode.CONCURRENT)
    void updateFilterName(String newFilterName) {
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
}
