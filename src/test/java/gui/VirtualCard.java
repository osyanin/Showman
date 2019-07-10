package gui;

import com.codeborne.selenide.Condition;
import core.TestBase;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

class VirtualCard extends TestBase {
    @Test
    @Disabled
    void issue(){
        open(LANDING_URL+"revo-online");
        screenshot("entered");
        $(byText("Выпустить карту")).click();
        switchTo().innerFrame("revo-form-iframe");
        $("div.formBlock.formBlock--continue > input").click();
        $(byText("телефон необходимо ввести в 10-значном формате")).isDisplayed();
        $("#online_loan_request_client_phone_with_terms_mobile_phone").sendKeys(generatedPhoneNumber);
        $("div.formBlock.formBlock--continue > input").click();
        $(byText("Фамилия")).click();
        $("#online_loan_request_virtual_card_client_human_attributes_surname").sendKeys(SURNAME);
        $(byText("Имя")).click();
        $("#online_loan_request_virtual_card_client_human_attributes_first_name").sendKeys(FIRST_NAME);
        $(byText("Отчество")).click();
        $("#online_loan_request_virtual_card_client_human_attributes_patronymic").sendKeys(PATRONYMIC);
        $(byText("Дата рождения")).click();
        $("#online_loan_request_virtual_card_client_birth_date").sendKeys(Keys.BACK_SPACE + BIRTHDATE);
        $(byText("Серия и номер паспорта")).click();
        $("#online_loan_request_virtual_card_client_passport_series_and_number").sendKeys(Keys.BACK_SPACE + "1108" + generatedPassportNumber);
        $(byText("Дата выдачи")).click();
        $("#online_loan_request_virtual_card_client_passport_issue_date").sendKeys("01.01.2008");
        $(byText("Кем выдан")).click();
        $("#online_loan_request_virtual_card_client_passport_issued_by").sendKeys("ЗАО Рога и копыта");
        $(byText("Ваша улица или переулок")).click();
        $("#online_loan_request_virtual_card_client_address_attributes_street").sendKeys("Ленина");
        $(byText("Ваш город или населенный пункт")).click();
        $("#online_loan_request_virtual_card_client_address_attributes_settlement").sendKeys("Москва г.");
        $(byText("Ваша область или регион")).click();
        $("#online_loan_request_virtual_card_client_address_attributes_area").sendKeys("Москва г.");
        $(byText("Дом")).click();
        $("#online_loan_request_virtual_card_client_address_attributes_house").sendKeys("1");
        $(byText("Здание")).click();
        $("#online_loan_request_virtual_card_client_address_attributes_building").sendKeys("2");
        $(byText("Квартира")).click();
        $("#online_loan_request_virtual_card_client_address_attributes_apartment").sendKeys("3");
        $(byText("E-mail")).click();
        $("#online_loan_request_virtual_card_client_email").sendKeys(generatedEmail);
        $("#new_online_loan_request_virtual_card_client > div.formBlock.formBlock--margin > label > span").click();
        $(byText("СМС-код")).click();
        $("#online_loan_request_virtual_card_client_bki_confirmation_code_confirmation").sendKeys(SMS_CODE);
        $("#new_online_loan_request_virtual_card_client > div:nth-child(21) > div.formBlock.formBlock--continue > input").click();
        $("#online_loan_request_documents_name").uploadFile(rabbitJpg);
        $("#online_loan_request_documents_client_with_passport").uploadFile(rabbitJpg);
        $("#submit-docs").click();
        $("#virtual-card-form > div.form__block.formBlock.formBlock--cardRadio > a:nth-child(4) > label > div.cardRadio__sum").click();
        $("#\\35 000 > div:nth-child(2) > p.issueCondition__info").shouldHave(Condition.text("5 000,00  руб."));
        $("#virtual-card-form > div.form__block.formBlock.formBlock--cardRadio > a:nth-child(3) > label > div.cardRadio__sum").click();
        $("#\\31 0000 > div:nth-child(2) > p.issueCondition__info").shouldHave(Condition.text("10 000,00  руб."));
        $("#virtual-card-form > div.form__block.formBlock.formBlock--cardRadio > a:nth-child(2) > label > div.cardRadio__sum").click();
        $("#\\31 5000 > div:nth-child(2) > p.issueCondition__info").shouldHave(Condition.text("15 000,00  руб."));
        $("#virtual-card-form > div:nth-child(5) > label > span").click();
        $("#virtual-card-form > div:nth-child(6) > label > span").click();
        $(byText("СМС-код")).click();
        $("#sms_code").sendKeys(SMS_CODE);
        $("#vc_submit").click();
        $(byText("Ваша карта успешно выпущена!")).isDisplayed();
    }
}
