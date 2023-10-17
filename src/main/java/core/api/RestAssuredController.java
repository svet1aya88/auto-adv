package core.api;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class RestAssuredController {

    public static ValidatableResponse post(RequestSpecification specification, String model, String uri, int status) {
        return RestAssured.given(specification)
                .log().all()
                .body(model)
                .post(uri)
                .then()
                .log().body()
                .assertThat()
                .statusCode(status);
    }

    public static ValidatableResponse put(RequestSpecification specification, String model, String uri, int status) {
        return RestAssured.given(specification)
                .log().all()
                .body(model)
                .put(uri)
                .then()
                .log().body()
                .assertThat()
                .statusCode(status);
    }

    public static ValidatableResponse patch(RequestSpecification specification, String requestBody, String uri, int status) {
        return RestAssured.given(specification)
                .log().all()
                .body(requestBody)
                .patch(uri)
                .then()
                .log().body()
                .assertThat()
                .statusCode(status);
    }

    public static ValidatableResponse get(RequestSpecification specification, String uri, int status) {
        return RestAssured.given(specification)
                .log().all()
                .get(uri)
                .then()
                .log().body()
                .assertThat()
                .statusCode(status);
    }

    public static ValidatableResponse delete(RequestSpecification specification, String uri, int status) {
        return RestAssured.given(specification)
                .log().all()
                .delete(uri)
                .then()
                .log().body()
                .assertThat()
                .statusCode(status);
    }
}
