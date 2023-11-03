package core.api;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class RestAssuredController {

    public static ValidatableResponse post(RequestSpecification specification, String model, String uri) {
        return RestAssured.given(specification)
                .log().all()
                .body(model)
                .post(uri)
                .then()
                .log().body();
    }

    public static ValidatableResponse put(RequestSpecification specification, String model, String uri) {
        return RestAssured.given(specification)
                .log().all()
                .body(model)
                .put(uri)
                .then()
                .log().body();
    }

    public static ValidatableResponse get(RequestSpecification specification, String uri) {
        return RestAssured.given(specification)
                .log().all()
                .get(uri)
                .then()
                .log().body();
    }

    public static ValidatableResponse delete(RequestSpecification specification, String uri) {
        return RestAssured.given(specification)
                .log().all()
                .delete(uri)
                .then()
                .log().body();
    }
}
