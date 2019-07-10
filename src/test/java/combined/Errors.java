package combined;

import core.Encode;
import core.TestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

class Errors extends TestBase {
    @Test
    void orderAmountIsDifferent() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_15000, 0.00, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        factoringStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringStatusCheck(generatedOrderId, "hold");

        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, 0.00, TERM_6, generatedPhoneNumber, generatedEmail);

        customFactoringFinish(constructFactoringFinishJSON(generatedOrderId, AMOUNT_14000), FACTORING_PRECHECK_FINISH, "Order amount is different from the amount specified before");
    }

    @Test
    void orderIdExistFinalized() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        factoringStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringStatusCheck(generatedOrderId, "hold");

        factoringFinish(generatedOrderId, AMOUNT_14000);

        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Order exists"));
    }

    @Test
    void errorSavingFile() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        factoringStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        jsonPayload = constructFactoringFinishJSON(generatedOrderId, AMOUNT_14000);

        given()
                .multiPart("check", "blah")
                .multiPart("body", jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_FINISH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Error saving file"));
    }

    @Test
    void notEnoughLimit() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        jsonPayload = constructFactoringChangeJSON(generatedOrderId, 1000000.0);

        customFactoringChangeOrCancel(jsonPayload, FACTORING_CHANGE, "Client has not enough limit");
    }

    @Test
    void unableFinish() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringFinish(constructFactoringFinishJSON(generatedOrderId, AMOUNT_14000));

        customFactoringFinish(generatedOrderId, AMOUNT_14000, FACTORING_PRECHECK_FINISH, jsonPayload, "Unable to finish - order is already finished/canceled");
    }

    @Test
    void unableCancel() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringFinish(constructFactoringFinishJSON(generatedOrderId, AMOUNT_14000));

        customFactoringChangeOrCancel(jsonPayload, FACTORING_CANCEL, "Unable to cancel - order is already finished/canceled");
    }

    @Test
    void unableChange() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringFinish(constructFactoringFinishJSON(generatedOrderId, AMOUNT_14000));

        jsonPayload = constructFactoringChangeJSON(generatedOrderId, AMOUNT_5000);

        customFactoringChangeOrCancel(jsonPayload, FACTORING_CHANGE, "Unable to change - order is already finished/canceled");
    }

    @Test
    void missingCartItems() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_14000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringFinish(constructFactoringFinishJSON(generatedOrderId, AMOUNT_14000));

        factoringChange.addProperty("order_id", generatedOrderId);
        factoringChange.addProperty("amount", AMOUNT_5000);
        jsonPayload = gson.toJson(factoringChange);

        customFactoringChangeOrCancel(jsonPayload, FACTORING_CHANGE, "Cart items are missing");
    }
}
