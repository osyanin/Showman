package gui;

import com.codeborne.selenide.Condition;
import core.TestBase;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class Merchant_Cabinet extends TestBase {
    @Test
    @Disabled
    void createAgents() {
        open(MC_LOGIN_URL);
        $("#mobile_phone").sendKeys("8881211431");
        $("#password").sendKeys("8888");
        $("input.linkButton").click();
        $("li.sidebarMenu__item:nth-child(2)").click();
        $(".titleHeader__link").click();
        $("#agents_form_agents_attributes_0_surname").sendKeys(SURNAME);
        $("div.formIn:nth-child(4) > div:nth-child(2) > div:nth-child(2)").click();
        $("div.selectize-dropdown:nth-child(7)").click();
        $("#agents_form_agents_attributes_0_first_name").sendKeys(FIRST_NAME);
        $("#agents_form_agents_attributes_0_patronymic").sendKeys(PATRONYMIC);
        $("div.formIn:nth-child(4) > div:nth-child(3) > div:nth-child(2)").click();
        $("div.selectize-dropdown:nth-child(8)").click();
        $("#agents_form_agents_attributes_0_birth_date").sendKeys(BIRTHDATE);
        $("#agents_form_agents_attributes_0_mobile_phone").sendKeys(generatedPhoneNumber);
        $("#agents_form_agents_attributes_0_email").sendKeys(generatedEmail);
        $("div.formIn:nth-child(4) > div:nth-child(4) > div:nth-child(2)").click();
        $("div.selectize-dropdown:nth-child(9) > div:nth-child(1) > div:nth-child(1)").click();
        $("input.linkButton").click();
        $(".cabinetHeaderLink").click();
        $("a.linkButton").click();
        $("#mobile_phone").sendKeys(generatedPhoneNumber);
        $("button.linkButton").click();
        driver.open("/admin/agents?scope=ves_spisok");
        driver.$("#user_login").sendKeys(ADMIN_LOGIN);
        driver.$("#user_password").sendKeys(ADMIN_PASSWORD);
        driver.$(".submit").click();
        driver.$("td:nth-child(7) > a:nth-child(1)").click();
        String value = driver.$("td.col.col-tekst").getText();
        System.out.println(value);
        String smsCode = value.substring(24, 28);
        driver.close();

        $("#activation_code").sendKeys(smsCode);
        $("button.linkButton").click();
        System.out.println($$("body"));
        System.out.println($$("#password"));
        System.out.println($$(withText("Придумайте пароль")));

        $(byText("Придумайте пароль"), 0).click();
        System.out.println($$("#password"));

        $("body").sendKeys(Keys.TAB);
        $("#password").setValue("1111");
        $("body").sendKeys(Keys.TAB);
        $("#password_confirmation").setValue("1111");
        $("body > div > div.contentIn > div > div:nth-child(2) > div > form > div.buttonContainer > button").click();
        $("body").sendKeys(Keys.TAB);
        $("#mobile_phone").sendKeys(generatedPhoneNumber);
        $("#password").sendKeys("1111");
        $("input.linkButton").click();
        $("#profile_passport_series").sendKeys("1015");
        $("#profile_passport_number").sendKeys(generatedPassportNumber);
        $("#profile_passport_branch_code").sendKeys("111111");
        $("#profile_passport_issue_date").sendKeys("11112015");
        $("#profile_passport_branch_name").sendKeys("УФМС");
        $("#profile_birth_place").sendKeys("г. Москва");
        $("#profile_registration_address").sendKeys("г. Москва");
        $("#profile_registration_date").sendKeys("11111999");
        $(".linkButton").click();
        $("div.cabinetInfo:nth-child(1) > div:nth-child(4) > a:nth-child(1)").waitUntil(Condition.visible, 5000);
        $("div.cabinetInfo:nth-child(1) > div:nth-child(4) > a:nth-child(1)").click();
        $("#profile_passport_photo").uploadFile(rabbitJpg);
        $("#profile_registration_photo").uploadFile(rabbitJpg);
        $("#profile_face_and_passport_photo").uploadFile(rabbitJpg);
        $("#profile_snils_photo").uploadFile(rabbitJpg);
        $("#profile_inn_photo").uploadFile(rabbitJpg);
        $("input.linkButton").click();
        $(byText("Фотографии загружены")).shouldBe(visible);
        $(".cabinetInfoLimit__button").click();
    }
}