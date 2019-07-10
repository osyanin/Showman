package api;

import core.TestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class Beacons extends TestBase {
    @RepeatedTest(250)
    @Disabled
    void register() {

        payload.addProperty("email", generatedEmail);
        payload.addProperty("password", generatedOrderId);
        payload.addProperty("name", generatedOrderId);

        jsonPayload = gson.toJson(payload);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post("http://85.143.222.113/api/v1/register")
                .then().log().all()
                .statusCode(200);
    }
    @Disabled
    @Test()
    void getUsers() {

        payload.addProperty("limit", 1000);

        jsonPayload = gson.toJson(payload);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .get("http://85.143.222.113/api/v1/users")
                .then().log().all()
                .statusCode(200);
    }
}
