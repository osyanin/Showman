package combined;

import core.TestBase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class Factoring extends TestBase {

    @Test
    void precheckFinishReturn() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        factoringStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber
        );

        factoringStatusCheck(generatedOrderId, "hold");

        factoringFinish(generatedOrderId, AMOUNT_15000);

        factoringStatusCheck(generatedOrderId, "finished");

        factoringReturn(generatedOrderId, AMOUNT_15000);

        factoringStatusCheck(generatedOrderId, "refunded");

        callbackExpected = generateCallbackExpected(
                generatedOrderId,
                DECISION_APPROVED,
                AMOUNT_15000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        callbackReceived = callbackReceive();

        assertThat(callbackReceived, containsString(callbackExpected));
    }

    @Test
    void precheckChangeFinish() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber
        );

        factoringStatusCheck(generatedOrderId, "hold");

        factoringChange(generatedOrderId, AMOUNT_5000);

        factoringStatusCheck(generatedOrderId, "hold");

        factoringFinish(generatedOrderId, AMOUNT_15000);

        factoringStatusCheck(generatedOrderId, "finished");

        callbackExpected = generateCallbackExpected(
                generatedOrderId,
                DECISION_APPROVED,
                AMOUNT_15000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        callbackReceived = callbackReceive();

        assertThat(callbackReceived, containsString(callbackExpected));
    }

    @Test
    void precheckChangeFinishReturn() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber
        );

        factoringChange(generatedOrderId, AMOUNT_5000);

        factoringFinish(generatedOrderId, AMOUNT_15000);

        factoringReturn(generatedOrderId, AMOUNT_15000);

        callbackExpected = generateCallbackExpected(
                generatedOrderId,
                DECISION_APPROVED,
                AMOUNT_15000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        callbackReceived = callbackReceive();

        assertThat(callbackReceived, containsString(callbackExpected));
    }

    @Test
    void precheckChangeCancel() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber
        );

        factoringChange(generatedOrderId, AMOUNT_5000);

        factoringCancel(generatedOrderId);

        callbackExpected = generateCallbackExpected(
                generatedOrderId,
                DECISION_APPROVED,
                AMOUNT_15000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        callbackReceived = callbackReceive();

        assertThat(callbackReceived, containsString(callbackExpected));
    }

    @Test
    void checkoutFinish() {
        jsonPayload = constructMaximalJSON(
                generatedOrderId,
                AMOUNT_14000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringFinish(generatedOrderId, AMOUNT_14000);

        factoringStatusCheck(generatedOrderId, "finished");

        callbackExpected = generateCallbackExpected(
                generatedOrderId,
                DECISION_APPROVED,
                AMOUNT_14000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        callbackReceived = callbackReceive();

        assertThat(callbackReceived, containsString(callbackExpected));
    }

    @Test
    void precheckFrameFinishReturnPartial() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringFinish(generatedOrderId, AMOUNT_15000);

        factoringReturn(generatedOrderId, 1000.00);

        factoringReturn(generatedOrderId, 1000.00);

        callbackExpected = generateCallbackExpected(
                generatedOrderId,
                DECISION_APPROVED,
                AMOUNT_15000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        callbackReceived = callbackReceive();

        assertThat(callbackReceived, containsString(callbackExpected));
    }

    @Test
    void checkoutCancel() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH, PASSWORD);

        factoringStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringStatusCheck(generatedOrderId, "hold");

        factoringCancel(generatedOrderId);

        factoringStatusCheck(generatedOrderId, "canceled");

        callbackExpected = generateCallbackExpected(
                generatedOrderId,
                DECISION_APPROVED,
                AMOUNT_15000,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        callbackReceived = callbackReceive();

        assertThat(callbackReceived, containsString(callbackExpected));
    }
}
