package api;

import business.models.api.FilterCondition;
import business.models.api.FilterConditionType;
import business.models.api.FilterOrder;
import business.models.api.FilterPostRequestModel;
import business.models.api.FilterResponseConstant;
import business.models.api.FilterSortingColumn;
import business.models.api.FilterType;
import business.models.api.FilteringFieldType;
import core.api.RestAssuredController;
import core.runner.BaseTest;
import core.utilities.Converter;
import core.utilities.properties.ApiProperty;
import core.utilities.properties.PropertyType;
import core.utilities.properties.TestPropertyReader;
import core.utilities.random.Randomizer;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class ApiTests extends BaseTest {

    private static final String PROJECT_NAME = "superadmin_personal";

    private RequestSpecification specification;
    private String baseUri;
    private TestPropertyReader apiProps;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        apiProps = new TestPropertyReader(PropertyType.API);
        specification = new RequestSpecBuilder()
                .addHeader("Authorization", apiProps.getProperty(ApiProperty.OAUTH_TOKEN))
                .setContentType(ContentType.JSON)
                .build();
        baseUri = apiProps.getProperty(ApiProperty.FILTER_URI);
    }

    @Test(groups = {"api"}, description = "Get all filters")
    public void getAllFilters() {
        RestAssuredController
                .get(specification, MessageFormat.format(baseUri, PROJECT_NAME))
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("content.size()", greaterThan(0));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if project name is invalid")
    public void errorWithWrongProjectName() {
        String invalidProjectName = "abc";
        RestAssuredController
                .get(specification, MessageFormat.format(baseUri, invalidProjectName))
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(FilterResponseConstant.ERROR_CODE.getValue(), equalTo(4040))
                .body(FilterResponseConstant.MESSAGE.getValue(), equalTo(String.format(
                        "Project '%s' not found. Did you use correct project name?", invalidProjectName)));
    }

    @Test(groups = {"api"}, description = "Create new filter")
    public void createFilter() {
        FilterPostRequestModel filterRequestModel = buildBaseFilterModel(Randomizer.generateAlphanumericStringWithSpaces());

        RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME))
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", greaterThan(0));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if creating new filter with empty name")
    public void errorIfCreateFilterWithEmptyName() {
        FilterPostRequestModel filterRequestModel = buildBaseFilterModel(Randomizer.generateAlphanumericStringWithSpaces());
        filterRequestModel.setName("");

        RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME))
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(FilterResponseConstant.ERROR_CODE.getValue(), equalTo(4001))
                .body(FilterResponseConstant.MESSAGE.getValue(), containsString("Incorrect Request. " +
                        "[Field 'name' should not contain only white spaces and shouldn't be empty. " +
                        "Field 'name' should have size from '3' to '128'.]"));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if creating new filter with invalid filtering field")
    public void errorIfCreateFilterWithInvalidFilteringField() {
        String invalidFilteringFieldValue = "invalid_value";
        FilterPostRequestModel filterRequestModel = buildBaseFilterModel(Randomizer.generateAlphanumericStringWithSpaces());
        filterRequestModel.getConditions().get(0).setFilteringField(invalidFilteringFieldValue);

        RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME))
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(FilterResponseConstant.ERROR_CODE.getValue(), equalTo(40011))
                .body(FilterResponseConstant.MESSAGE.getValue(), equalTo(String.format("Incorrect filtering parameters. " +
                        "Filter parameter '%s' is not defined", invalidFilteringFieldValue)));
    }

    @Test(groups = {"api"}, description = "Update filter")
    public void updateFilter() {
        String filterId = "61";
        String uri = MessageFormat.format(baseUri, PROJECT_NAME).concat("/").concat(filterId);
        FilterPostRequestModel filterRequestModel = buildBaseFilterModel(Randomizer.generateAlphanumericStringWithSpaces());

        RestAssuredController
                .put(specification, Converter.convertJsonToString(filterRequestModel), uri)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(FilterResponseConstant.MESSAGE.getValue(),
                        equalTo(String.format("User filter with ID = '%s' successfully updated.", filterId)));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if updating filter with empty name")
    public void errorIfUpdateFilterWithEmptyName() {
        String uri = MessageFormat.format(baseUri, PROJECT_NAME).concat("/61");
        FilterPostRequestModel filterRequestModel = buildBaseFilterModel(Randomizer.generateAlphanumericStringWithSpaces());
        filterRequestModel.setName("");

        RestAssuredController
                .put(specification, Converter.convertJsonToString(filterRequestModel), uri)
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(FilterResponseConstant.ERROR_CODE.getValue(), equalTo(4001))
                .body(FilterResponseConstant.MESSAGE.getValue(), containsString("Incorrect Request. " +
                        "[Field 'name' should not contain only white spaces and shouldn't be empty. " +
                        "Field 'name' should have size from '3' to '128'.]"));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if updating filter with invalid sorting column")
    public void errorIfUpdateFilterWithInvalidSortingColumn() {
        String invalidSortingColumn = "abc";
        FilterPostRequestModel filterRequestModel = buildBaseFilterModel(Randomizer.generateAlphanumericStringWithSpaces());

        RestAssuredController
                .put(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME).concat("/61"))
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(FilterResponseConstant.ERROR_CODE.getValue(), equalTo(40012))
                .body(FilterResponseConstant.MESSAGE.getValue(), equalTo(String.format(
                        "Sorting parameter Unable to find sort parameter '%s' is not defined", invalidSortingColumn)));
    }

    @Test(groups = {"api"}, description = "Delete filter")
    public void deleteFilter() {
        String baseUri = MessageFormat.format(this.baseUri, PROJECT_NAME);
        FilterPostRequestModel filterRequestModel = buildBaseFilterModel(Randomizer.generateAlphanumericStringWithSpaces());

        String filterId = RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel), baseUri)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .extract().body().jsonPath().getString("id");
        String filterUri = baseUri.concat("/").concat(filterId);
        RestAssuredController
                .delete(specification, filterUri)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(FilterResponseConstant.MESSAGE.getValue(),
                        equalTo(String.format("User filter with ID = '%s' successfully deleted.", filterId)));
        RestAssuredController
                .get(specification, filterUri)
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(FilterResponseConstant.ERROR_CODE.getValue(), equalTo(40421))
                .body(FilterResponseConstant.MESSAGE.getValue(), equalTo(String.format(
                        "User filter with ID '%s' not found on project '%s'. " +
                                "Did you use correct User Filter ID?", filterId, PROJECT_NAME)));
    }

    @Test(groups = {"api"}, description = "Error if delete filter with invalid id")
    public void errorIfDeleteFilterWithInvalidId() {
        String filterId = "181818181";
        RestAssuredController
                .delete(specification, MessageFormat.format(this.baseUri, PROJECT_NAME).concat("/").concat(filterId))
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body(FilterResponseConstant.ERROR_CODE.getValue(), equalTo(40421))
                .body(FilterResponseConstant.MESSAGE.getValue(), equalTo(String.format(
                        "User filter with ID '%s' not found on project '%s'. " +
                                "Did you use correct User Filter ID?", filterId, PROJECT_NAME)));

    }

    private FilterPostRequestModel buildBaseFilterModel(String filterName) {
        return FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition(FilterConditionType.HAS.getValue())
                        .filteringField(FilteringFieldType.COMPOSITE_ATTRIBUTE.getValue())
                        .value(filterName)
                        .build()))
                .description("test description")
                .name(filterName)
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn(FilterSortingColumn.START_TIME.getValue())
                        .build()))
                .type(FilterType.LAUNCH.getValue())
                .build();
    }
}
