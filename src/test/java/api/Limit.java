package api;

import core.Encode;
import core.TestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

class Limit extends TestBase {

    @Test
    void limitAuthNew() {
        given()
                .header("Content-Type", "application/json")
                .body(constructLimitRequestJSON(generatedPhoneNumber))
                .when()
                .post(LIMIT + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200)
                .body("meta.message", is("Payload valid"),
                        "client.status", is("new"),
                        "client.limit_amount", is("0.00"),
                        "client.mobile_phone", is(generatedPhoneNumber));
    }
}
