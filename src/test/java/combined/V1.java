package combined;

import core.Encode;
import core.TestBase;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

class V1 extends TestBase {

    @Test
    void checkoutPartialReturn() {
        logger.warn(URL);

        jsonPayload = constructV1MinimalJSON(
                generatedOrderId,
                "5000.00",
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, V1_AUTH, PASSWORD);

        //iframeUrl = iframeUrl.replace("https://lvh.me:3000", URL);

        checkIframeV1(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        v12StatusCheck(generatedOrderId, "finished");

        given()
                .header("Content-Type", "application/json")
                .body(constructV12ReturnJSON(generatedOrderId, "1000.00"))
                .when()
                .post(V1_RETURN + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Payload valid"));

        v12StatusCheck(generatedOrderId, "finished");

        given()
                .header("Content-Type", "application/json")
                .body(constructV12ReturnJSON(generatedOrderId, "4000.00"))
                .when()
                .post(V1_RETURN + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Payload valid"));

        v12StatusCheck(generatedOrderId, "refunded");

        jsonPayload = constructV1MinimalJSON(
                generatedOrderId+"1111",
                "3000.00",
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, V1_AUTH, PASSWORD);

        open(iframeUrl);
        $(byText("СМС-код")).click();
        $(byId("online_loan_request_confirmation_bki_confirmation_code_confirmation")).setValue(SMS_CODE);
        $(byClassName("js-iframe-form-submit")).click();
        $(byClassName("js-online-offer-checkbox-agreement")).click();
        $(byText("СМС-код")).click();
        $(byId("online_loan_request_offer_bki_confirmation_code_confirmation")).setValue(SMS_CODE);
        $(byClassName("linkButton--orange")).click();

        v12StatusCheck(generatedOrderId+"1111", "finished");


    }
}
