package api;

import business.models.api.FilterCondition;
import business.models.api.FilterOrder;
import business.models.api.FilterPostRequestModel;
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
                .get(specification, MessageFormat.format(baseUri, PROJECT_NAME), HttpStatus.SC_OK)
                .assertThat()
                .body("content.size()", greaterThan(0));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if project name is invalid")
    public void errorWithWrongProjectName() {
        String invalidProjectName = "abc";
        RestAssuredController
                .get(specification, MessageFormat.format(baseUri, invalidProjectName), HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body("errorCode", equalTo(4040))
                .body("message", equalTo(String.format(
                        "Project '%s' not found. Did you use correct project name?", invalidProjectName)));
    }

    @Test(groups = {"api"}, description = "Create new filter")
    public void createFilter() {
        String randomName = Randomizer.generateAlphanumericStringWithSpaces();
        FilterPostRequestModel filterRequestModel = FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition("has")
                        .filteringField("compositeAttribute")
                        .value(randomName)
                        .build()))
                .description("test description")
                .name(randomName)
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn("startTime")
                        .build()))
                .type("launch")
                .build();

        RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME), HttpStatus.SC_CREATED)
                .assertThat()
                .body("id", greaterThan(0));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if creating new filter with empty name")
    public void errorIfCreateFilterWithEmptyName() {
        FilterPostRequestModel filterRequestModel = FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition("has")
                        .filteringField("compositeAttribute")
                        .value(Randomizer.generateAlphanumericStringWithSpaces())
                        .build()))
                .description("test description")
                .name("")
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn("startTime")
                        .build()))
                .type("launch")
                .build();

        RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME), HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("errorCode", equalTo(4001))
                .body("message", containsString("Incorrect Request. " +
                        "[Field 'name' should not contain only white spaces and shouldn't be empty. " +
                        "Field 'name' should have size from '3' to '128'.]"));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if creating new filter with invalid filtering field")
    public void errorIfCreateFilterWithInvalidFilteringField() {
        String filterName = Randomizer.generateAlphanumericStringWithSpaces();
        String invalidFilteringFieldValue = "invalid_value";
        FilterPostRequestModel filterRequestModel = FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition("has")
                        .filteringField(invalidFilteringFieldValue)
                        .value(filterName)
                        .build()))
                .description("test description")
                .name(filterName)
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn("startTime")
                        .build()))
                .type("launch")
                .build();

        RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME), HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("errorCode", equalTo(40011))
                .body("message", equalTo(String.format("Incorrect filtering parameters. " +
                        "Filter parameter '%s' is not defined", invalidFilteringFieldValue)));
    }

    @Test(groups = {"api"}, description = "Update filter")
    public void updateFilter() {
        String filterId = "61";
        String uri = MessageFormat.format(baseUri, PROJECT_NAME).concat("/").concat(filterId);
        String randomName = Randomizer.generateAlphanumericStringWithSpaces();
        FilterPostRequestModel filterRequestModel = FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition("has")
                        .filteringField("compositeAttribute")
                        .value(randomName)
                        .build()))
                .description("test description")
                .name(randomName)
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn("startTime")
                        .build()))
                .type("launch")
                .build();

        RestAssuredController
                .put(specification, Converter.convertJsonToString(filterRequestModel), uri, HttpStatus.SC_OK)
                .assertThat()
                .body("message", equalTo(String.format("User filter with ID = '%s' successfully updated.", filterId)));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if updating filter with empty name")
    public void errorIfUpdateFilterWithEmptyName() {
        String uri = MessageFormat.format(baseUri, PROJECT_NAME).concat("/61");
        String randomName = Randomizer.generateAlphanumericStringWithSpaces();
        FilterPostRequestModel filterRequestModel = FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition("has")
                        .filteringField("compositeAttribute")
                        .value(randomName)
                        .build()))
                .description("test description")
                .name("")
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn("startTime")
                        .build()))
                .type("launch")
                .build();

        RestAssuredController
                .put(specification, Converter.convertJsonToString(filterRequestModel), uri, HttpStatus.SC_BAD_REQUEST)
                .body("errorCode", equalTo(4001))
                .body("message", containsString("Incorrect Request. " +
                        "[Field 'name' should not contain only white spaces and shouldn't be empty. " +
                        "Field 'name' should have size from '3' to '128'.]"));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if updating filter with invalid sorting column")
    public void errorIfUpdateFilterWithInvalidSortingColumn() {
        String randomName = Randomizer.generateAlphanumericStringWithSpaces();
        String invalidSortingColumn = "abc";
        FilterPostRequestModel filterRequestModel = FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition("has")
                        .filteringField("compositeAttribute")
                        .value(randomName)
                        .build()))
                .description("test description")
                .name(randomName)
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn(invalidSortingColumn)
                        .build()))
                .type("launch")
                .build();

        RestAssuredController
                .put(specification, Converter.convertJsonToString(filterRequestModel),
                        MessageFormat.format(baseUri, PROJECT_NAME).concat("/61"), HttpStatus.SC_BAD_REQUEST)
                .body("errorCode", equalTo(40012))
                .body("message", equalTo(String.format(
                        "Sorting parameter Unable to find sort parameter '%s' is not defined", invalidSortingColumn)));
    }

    @Test(groups = {"api"}, description = "Delete filter")
    public void deleteFilter() {
        String baseUri = MessageFormat.format(this.baseUri, PROJECT_NAME);
        String randomName = Randomizer.generateAlphanumericStringWithSpaces();
        FilterPostRequestModel filterRequestModel = FilterPostRequestModel.builder()
                .conditions(Collections.singletonList(FilterCondition.builder()
                        .condition("has")
                        .filteringField("compositeAttribute")
                        .value(randomName)
                        .build()))
                .description("test description")
                .name(randomName)
                .orders(Collections.singletonList(FilterOrder.builder()
                        .isAsc(true)
                        .sortingColumn("startTime")
                        .build()))
                .type("launch")
                .build();

        String filterId = RestAssuredController
                .post(specification, Converter.convertJsonToString(filterRequestModel), baseUri, HttpStatus.SC_CREATED)
                .extract().body().jsonPath().getString("id");
        String filterUri = baseUri.concat("/").concat(filterId);
        RestAssuredController
                .delete(specification, filterUri, HttpStatus.SC_OK)
                .assertThat()
                .body("message", equalTo(String.format("User filter with ID = '%s' successfully deleted.", filterId)));
        RestAssuredController
                .get(specification, filterUri, HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body("errorCode", equalTo(40421))
                .body("message", equalTo(String.format(
                        "User filter with ID '%s' not found on project '%s'. " +
                                "Did you use correct User Filter ID?", filterId, PROJECT_NAME)));
    }

    @Test(groups = {"api"}, description = "Error if delete filter with invalid id")
    public void errorIfDeleteFilterWithInvalidId() {
        String filterId = "181818181";
        RestAssuredController
                .delete(specification, MessageFormat.format(this.baseUri, PROJECT_NAME).concat("/").concat(filterId),
                        HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body("errorCode", equalTo(40421))
                .body("message", equalTo(String.format(
                        "User filter with ID '%s' not found on project '%s'. " +
                                "Did you use correct User Filter ID?", filterId, PROJECT_NAME)));

    }
}
