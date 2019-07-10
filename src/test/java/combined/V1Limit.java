package combined;

import core.Encode;
import core.TestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

class V1Limit extends TestBase {
    @Test
    void limitAuth() {
        jsonPayload = constructV1MinimalJSON(
                generatedOrderId,
                "5000.00",
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, V1_AUTH, PASSWORD);

        checkIframeV1(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        v12StatusCheck(generatedOrderId, "finished");

        given()
                .header("Content-Type", "application/json")
                .body(constructLimitRequestJSON(generatedPhoneNumber))
                .when()
                .post(V1_LIMIT + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200)
                .body("meta.message", is("Payload valid"),
                        "client.status", is("active"),
                        "client.limit_amount", is("5000.00"),
                        "client.mobile_phone", is(generatedPhoneNumber));
    }
}
