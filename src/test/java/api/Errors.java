package api;

import core.Encode;
import core.TestBase;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

class Errors extends TestBase {

    @Test
    void noOrderId() {
        checkout.addProperty("callback_url", CALLBACK_URL);
        checkout.addProperty("redirect_url", REDIRECT_URL);
        checkout.addProperty("primary_phone", generatedPhoneNumber);
        checkout.addProperty("primary_email", generatedEmail);
        current_order.addProperty("valid_till", VALID_TILL);
        current_order.addProperty("term", TERM_6);
        current_order.addProperty("amount", AMOUNT_15000);
        current_order.addProperty("prepayment_amount", PREPAYMENT_AMOUNT_1000);
        checkout.add("current_order", current_order);

        jsonPayload = gson.toJson(checkout);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200)
                .assertThat().body(
                "status", is(20),
                "message", is("Order order_id missing"));
    }

    @Test
    void orderNotFound() {
        jsonPayload = constructFactoringFinishJSON(generatedOrderId + "boom", AMOUNT_14000);

        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("body", jsonPayload)
                .multiPart("check", rabbitPdf)
                .when().log().all()
                .post(FACTORING_PRECHECK_FINISH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is("Order with specified id not found"));
    }

    @Test
    void wrongOrderSumFormat() {
        checkout.addProperty("callback_url", CALLBACK_URL);
        checkout.addProperty("redirect_url", REDIRECT_URL);
        checkout.addProperty("primary_phone", generatedPhoneNumber);
        checkout.addProperty("primary_email", generatedEmail);
        current_order.addProperty("order_id", generatedOrderId);
        current_order.addProperty("valid_till", VALID_TILL);
        current_order.addProperty("term", TERM_6);
        current_order.addProperty("sum", AMOUNT_15000);
        current_order.addProperty("prepayment_amount", PREPAYMENT_AMOUNT_1000);
        checkout.add("current_order", current_order);

        jsonPayload = gson.toJson(checkout);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Order amount is missing"), "status", is(30));
    }

    @Test
    void outsideTariffAmountUp() {
        checkout.addProperty("callback_url", CALLBACK_URL);
        checkout.addProperty("redirect_url", REDIRECT_URL);
        checkout.addProperty("primary_phone", generatedPhoneNumber);
        checkout.addProperty("primary_email", generatedEmail);
        current_order.addProperty("order_id", generatedOrderId);
        current_order.addProperty("valid_till", VALID_TILL);
        current_order.addProperty("term", TERM_6);
        current_order.addProperty("amount", 9999999999999.0);
        checkout.add("current_order", current_order);

        jsonPayload = gson.toJson(checkout);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is("Order amount is outside of tariff_limits"));
    }

    @Test
    void outsideTariffAmountDown() {
        checkout.addProperty("callback_url", CALLBACK_URL);
        checkout.addProperty("redirect_url", REDIRECT_URL);
        checkout.addProperty("primary_phone", generatedPhoneNumber);
        checkout.addProperty("primary_email", generatedEmail);
        current_order.addProperty("order_id", generatedOrderId);
        current_order.addProperty("valid_till", VALID_TILL);
        current_order.addProperty("term", TERM_6);
        current_order.addProperty("amount", 0.0);
        checkout.add("current_order", current_order);

        jsonPayload = gson.toJson(checkout);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Order amount is outside of tariff_limits"));
    }

    @Test
    void wrongOrderTermVal() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_15000, PREPAYMENT_AMOUNT_0, 5, generatedPhoneNumber, generatedEmail);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Order term value is wrong"));
    }

    @Test
    void amountLessThatPrepayment() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_5000, 5001.0, TERM_6, generatedPhoneNumber, generatedEmail);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Order prepayment amount is wrong"));
    }

    @Test
    void noCallbackUrl() {
        checkout.addProperty("redirect_url", REDIRECT_URL);
        checkout.addProperty("primary_phone", generatedPhoneNumber);
        checkout.addProperty("primary_email", generatedEmail);
        current_order.addProperty("order_id", generatedOrderId);
        current_order.addProperty("valid_till", VALID_TILL);
        current_order.addProperty("term", TERM_6);
        current_order.addProperty("amount", AMOUNT_15000);
        current_order.addProperty("prepayment_amount", PREPAYMENT_AMOUNT_1000);
        checkout.add("current_order", current_order);

        jsonPayload = gson.toJson(checkout);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Order callback_url missing"));
    }

    @Test
    void storeIdWrong() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_15000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH_NOT_EXISTENT_STORE_ID + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Store not found"));
    }

    @Test
    void storeIdMissing() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_15000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH_NO_STORE_ID + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Store id is missing"));
    }

    @Test
    void signatureMissing() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_15000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH)
                .then()
                .statusCode(200).body("message", is("Signature missing"));
    }

    @Test
    void signatureWrong() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_15000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        given()
                .contentType("application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + "aphew")
                .then()
                .statusCode(200).body("message", is("Signature wrong"));
    }

    @Test
    void phoneNumberIsDifferent() {
        jsonPayload = constructMinimalJSON(generatedOrderId, AMOUNT_15000, PREPAYMENT_AMOUNT_0, TERM_6, generatedPhoneNumber, generatedEmail);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Payload valid"));

        payload.remove("primary_phone");
        payload.addProperty("primary_phone", "8811234567");

        jsonPayload = gson.toJson(payload);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when()
                .post(FACTORING_PRECHECK_AUTH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then()
                .statusCode(200).body("message", is("Phone number is different"));
    }
}
