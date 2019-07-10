package combined;

import core.TestBase;
import org.junit.jupiter.api.Test;

class Prepayment extends TestBase {
    @Test
    void ladderMin() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                2500.0,
                0.0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH_PREPAYMENT_LADDER, PASSWORD_PREPAYMENT_LADDER);

        factoringPrepaymentLadderStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringPrepaymentLadderStatusCheck(generatedOrderId, "hold");

    }

    @Test
    void ladderMid() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                3500.0,
                0.0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH_PREPAYMENT_LADDER, PASSWORD_PREPAYMENT_LADDER);

        factoringPrepaymentLadderStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringPrepaymentLadderStatusCheck(generatedOrderId, "hold");

    }

    @Test
    void ladderMax() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                5500.0,
                0.0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_PRECHECK_AUTH_PREPAYMENT_LADDER, PASSWORD_PREPAYMENT_LADDER);

        factoringPrepaymentLadderStatusCheck(generatedOrderId, "pending");

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        factoringPrepaymentLadderStatusCheck(generatedOrderId, "hold");

    }
}
