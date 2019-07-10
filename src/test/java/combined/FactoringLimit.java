package combined;

import core.TestBase;
import org.junit.jupiter.api.Test;

class FactoringLimit extends TestBase {

    @Test
    void limitAuthActive() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_LIMIT_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        getLimit(generatedPhoneNumber, jsonPayload, "active");
    }

    @Test
    void limitAuthInactive() {
        jsonPayload = constructMinimalJSON(
                generatedOrderId,
                AMOUNT_15000,
                PREPAYMENT_AMOUNT_0,
                TERM_6,
                generatedPhoneNumber,
                generatedEmail
        );

        iframeUrl = iframeReguest(jsonPayload, FACTORING_LIMIT_AUTH, PASSWORD);

        checkIframe(
                iframeUrl,
                generatedEmail,
                generatedPassportNumber);

        webAdminLoanBlock(generatedPhoneNumber);

        getLimit(generatedPhoneNumber, jsonPayload, "inactive");
    }
}
