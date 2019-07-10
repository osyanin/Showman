package gui;

import core.TestBase;
import io.qameta.allure.Issue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

@Issue("RTECH-3549")
class Offline extends TestBase{
    @Test
    void preCalc() {
        offlineFormLogin(MC_MANAGER_LOGIN, MC_MANAGER_PASSWORD);

        $(byText("Предварительный расчет")).click();
        $("#modalPinShow").click();
        $(byText("Авторизация консультанта")).exists();
        $("#pin").setValue(MC_MANAGER_PIN);
        $(".show-pass").click();
        $(byText(MC_MANAGER_PIN)).exists();

        $("#formPin > span.form-reset").click();
        $("#pin").is(empty);
        $("#pin").click();
        $("#pin").setValue(MC_MANAGER_PIN);
        $(byValue("Вход")).click();
        $("#mobile_phone").setValue(generatedPhoneNumber);
        $(byText("Продолжить")).click();

        $("#first_name").setValue(FIRST_NAME);
        $("li.field:nth-child(6) > div:nth-child(4)").click();
        $(byText("Поле заполнено неверно")).exists();
        $("#last_name").setValue(SURNAME);
        $("li.field:nth-child(5) > div:nth-child(4)").click();
        $(byText("Поле заполнено неверно")).exists();
        $("#middle_name").setValue(PATRONYMIC);
        $("li.field:nth-child(7) > div:nth-child(4)").click();
        $(byText("Поле заполнено неверно")).exists();
        $("#birth_date").setValue(BIRTHDATE);
        $("li.field:nth-child(8) > div:nth-child(4)").click();
        $(byText("Укажите корректную дату")).exists();
        $("#passport_series").setValue("1008");
        $("li.field_passport_series:nth-child(1) > div:nth-child(2)").click();
        $(byText("Данное поле обязательно к заполнению")).exists();
        $("#passport_number").setValue(generatedPassportNumber);
        $("li.field_passport_number > div:nth-child(2)").click();
        $(byText("Данное поле обязательно к заполнению")).exists();

        $("#first_name").setValue(FIRST_NAME);
        $("#last_name").setValue(SURNAME);
        $("#middle_name").setValue(PATRONYMIC);
        $("#birth_date").setValue(BIRTHDATE);
        $("#passport_series").setValue("1008");
        $("#passport_number").setValue(generatedPassportNumber);
        $(byValue("Отправить заявку")).click();
        $(byValue("Отправить заявку")).click();
        $("#asp_text").setValue("11");
        $("#asp_button").click();
        $(byText("Неправильный код подтверждения")).exists();
        $(".two").click();
        $(byText("Соглашения об использовании Аналога собственноручной подписи")).click();
        $("head > title").is(text("noloan.pdf"));
        switchTo().parentFrame();
        $("#asp_text").setValue(SMS_CODE);
        $("#asp_button").click();
        $(byText("Спасибо за ваше обращение!")).isDisplayed();
        $(byText("Клиенту будет выслана СМС с установленным лимитом")).isDisplayed();
        $(byText("Выход в меню")).click();
    }

    @Test
    void loanThenReturn() {
        //оформление
        offlineFormLogin(MC_MANAGER_LOGIN, MC_MANAGER_PASSWORD);

        $(byText("Оформление")).click();
        $(byText("Тариф")).isDisplayed();
        $("#calc_by_purchase_amount").shouldBe(empty).sendKeys("15000");
        $("#calc_by_purchase_amount").has(value("15000"));
        $(".clear-field", 1).click();
        $("#calc_by_purchase_amount").shouldBe(empty).sendKeys("15000");
        $(byText("6 мес."), 1).click();
        $("#expandInfoBtn").click();
        $("#modalPinShow").click();
        $(byText("Авторизация консультанта")).exists();
        $("#pin").setValue(MC_MANAGER_PIN);
        $(".show-pass").click();
        $(byText(MC_MANAGER_PIN)).exists();
        $("span.form-reset:nth-child(3)").click();
        $("#pin").setValue(MC_MANAGER_PIN);
        $("input.btn:nth-child(6)").click();
        $("#mobile_phone").setValue(generatedPhoneNumber);
        $(byText("Продолжить")).click();
        $(byText("Изменить")).click();
        $("#first_name").setValue(FIRST_NAME + "р");
        $("#last_name").setValue(SURNAME + "р");
        $("#middle_name").setValue(PATRONYMIC + "р");
        $("#birth_date").setValue(BIRTHDATE);
        $("#passport_series").setValue("1008");
        $("#passport_number").setValue(generatedPassportNumber);

        $(byValue("Отправить заявку")).click();
        $(byValue("Отправить заявку")).click();

        $("#asp_text").setValue("11");
        $("#asp_button").click();
        $(byText("Неправильный код подтверждения")).exists();
        $(".two").click();
        $("#asp_text").setValue(SMS_CODE);
        $("#asp_button").click();
        $("#capture-btn-grey-loading_btn-first_two_pages-camera-reader").uploadFile(rabbitJpg);
        $("#capture-btn-grey-loading_btn-living_addr-camera-reader").uploadFile(rabbitJpg);
        $("#capture-btn-grey-loading_btn-client_with_passport-camera-reader").uploadFile(rabbitJpg);
        $(byText("Загрузить и продолжить")).click();
        $("#asp_confirm").click();
        $(byText("Полный текст Договора и Согласия")).click();
        switchTo().parentFrame();
        $("#confirm_asp_text").setValue(SMS_CODE);
        $("#confirm_asp_button").click();
        $(byText("Выход в меню")).click();

        //возврат

        $("a.btn:nth-child(4)").click();
        $("#phone").setValue(generatedPhoneNumber);
        $("input.btn:nth-child(1)").click();
        $("form:nth-child(1) > input:nth-child(4) ").click();
        $(byText("Тариф")).isDisplayed();
        $("#calc_by_purchase_amount").setValue("100000");
        $(byText("Полный возврат")).shouldBe(visible);
        $("#expandInfoBtn").click();
        $("#modalPinShow").click();
        $("#pin").setValue("1111");
        $("#formPin > input.btn").click();
        $("ul.buttons:nth-child(1) > li:nth-child(1) > button:nth-child(1)").click();
        $("#asp_return_text").click();
        $("#return_finish_button").click();
        $(byText("Завершить возврат")).click();
        $(".two").click();
        $(byText("Оформление")).click();
        $("body > div > div.header > a.logout").click();
    }
}