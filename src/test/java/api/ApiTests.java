package api;

import business.models.api.FilterCondition;
import business.models.api.FilterOrder;
import business.models.api.FilterRequestModel;
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
    private String uri;
    private TestPropertyReader apiProps;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        apiProps = new TestPropertyReader(PropertyType.API);
        specification = new RequestSpecBuilder()
                .addHeader("Authorization", apiProps.getProperty(ApiProperty.OAUTH_TOKEN))
                .setContentType(ContentType.JSON)
                .build();
        uri = apiProps.getProperty(ApiProperty.FILTER_URI);
    }

    //    @Test(groups = {"api"}, description = "Get all filters")
    public void getAllFilters() {
        RestAssuredController
                .get(specification, MessageFormat.format(uri, PROJECT_NAME), HttpStatus.SC_OK)
                .assertThat()
                .body("content.size()", greaterThan(0));
    }

    //    @Test(groups = {"api"}, description = "Verify that error is returned in response if project name is invalid")
    public void errorWithWrongProjectName() {
        String invalidProjectName = "abc";
        RestAssuredController
                .get(specification, MessageFormat.format(uri, invalidProjectName), HttpStatus.SC_NOT_FOUND)
                .assertThat()
                .body("errorCode", equalTo(4040))
                .body("message", equalTo(String.format(
                        "Project '%s' not found. Did you use correct project name?", invalidProjectName)));
    }

    //    @Test(groups = {"api"}, description = "Create new filter")
    public void createFilter() {
        String randomName = Randomizer.generateAlphanumericStringWithSpaces();
        FilterRequestModel filterRequestModel = FilterRequestModel.builder()
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
                        MessageFormat.format(uri, PROJECT_NAME), HttpStatus.SC_CREATED)
                .assertThat()
                .body("id", greaterThan(0));
    }

    @Test(groups = {"api"}, description = "Verify that error is returned in response if creating new filter with empty name")
    public void errorIfCreateFilterWithEmptyName() {
        FilterRequestModel filterRequestModel = FilterRequestModel.builder()
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
                        MessageFormat.format(uri, PROJECT_NAME), HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("errorCode", equalTo(4001))
                .body("message", containsString("Incorrect Request. " +
                        "[Field 'name' should not contain only white spaces and shouldn't be empty. " +
                        "Field 'name' should have size from '3' to '128'.] "));
    }
}
