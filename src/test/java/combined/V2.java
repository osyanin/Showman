package combined;

import core.Encode;
import core.TestBase;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

class V2 extends TestBase {

    @Test
    void checkoutPartialReturn() {
        jsonPayload = constructV1MinimalJSON(
                generatedOrderId,
                "5000.00",
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, V2_AUTH, PASSWORD);

        checkIframeV2(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        v12StatusCheck(generatedOrderId, "finished");

        given()
                .header("Content-Type", "application/json")
                .body(constructV12ReturnJSON(generatedOrderId, "1000.00"))
                .when()
                .post(FACTORING_RETURN + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Payload valid"));

        v12StatusCheck(generatedOrderId, "finished");

        given()
                .header("Content-Type", "application/json")
                .body(constructV12ReturnJSON(generatedOrderId, "4000.00"))
                .when()
                .post(FACTORING_RETURN + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Payload valid"));

        v12StatusCheck(generatedOrderId, "refunded");
    }
}
