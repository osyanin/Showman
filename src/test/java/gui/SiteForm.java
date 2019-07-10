package gui;

import core.TestBase;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.*;

class SiteForm extends TestBase {
    @Test
    void register() {
        open(SITE_URL);
        $(byText("Получить услугу")).click();
        $(byText("Зарегистрироваться")).isDisplayed();
        $(byText("Зарегистрироваться")).click();
        $("#revo-form-iframe").isDisplayed();
        switchTo().innerFrame("revo-form-iframe");
        $("div.formBlock.formBlock--continue > input").click();
        $(byText("телефон необходимо ввести в 10-значном формате")).isDisplayed();
        $("#online_loan_request_client_phone_mobile_phone").sendKeys(generatedPhoneNumber);
        $(byValue("Продолжить")).click();
    }
}
