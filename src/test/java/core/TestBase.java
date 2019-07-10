package core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideConfig;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.*;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.codeborne.selenide.Selectors.byAttribute;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.addListener;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

public class TestBase {
    protected static Logger logger = LoggerFactory.getLogger(TestBase.class);

    private static final String CALLBACK_IP = System.getProperty("callback_ip");
    protected static String PASSWORD;
    private static String STORE_ID;
    protected static String PASSWORD_PREPAYMENT_LADDER;
    protected static String FACTORING_PRECHECK_AUTH_PREPAYMENT_LADDER;
    private static String STORE_ID_PREPAYMENT;
    private static String PASSWORD_PREPAYMENT;
    protected static String SMS_CODE;
    protected static String ADMIN_LOGIN;
    protected static String ADMIN_PASSWORD;
    private static String MC_TRADE_OWNER_PASSWORD;
    private static String MC_TRADE_OWNER;
    protected static String MC_MANAGER_LOGIN;
    protected static String MC_MANAGER_PASSWORD;
    protected static String MC_MANAGER_PIN;
    protected static String URL;
    protected static String SITE_URL;
    protected static String LANDING_URL;
    protected static String MC_LOGIN_URL;

    protected static String FACTORING_PRECHECK_AUTH;
    private static String STORE_ID_PREPAYMENT_LADDER;
    private static String ONLINE_STORE_URL;
    protected static String FACTORING_LIMIT_AUTH;
    protected static String FACTORING_PRECHECK_FINISH;
    protected static String FACTORING_RETURN;
    protected static String FACTORING_CANCEL;
    protected static String FACTORING_CHANGE;
    protected static String LIMIT;
    private static String SCHEDULE;
    protected static String CALLBACK_URL;
    private static String CALLBACK_JOURNAL;
    private static String FACTORING_STATUS_API;
    private static String FACTORING_STATUS_API_PREPAYMENT;
    private static String FACTORING_STATUS_API_PREPAYMENT_LADDER;
    protected static String V1_LIMIT;
    private static String V1_STATUS_API;
    protected static String V1_AUTH;
    protected static String V1_RETURN;
    private static String V2_LIMIT;
    private static String V2_STATUS_API;
    protected static String V2_AUTH;
    private static String V2_RETURN;

    private static WireMockServer wireMockServer = new WireMockServer(options().dynamicPort().bindAddress(CALLBACK_IP));

    protected static SelenideDriver driver;
    protected final double AMOUNT_15000 = 5000.0;

    private RandomGenerated generated = new RandomGenerated();
    private Base64 decoder = new Base64();
    protected Gson gson = new Gson();
    private JsonParser jsonParser = new JsonParser();
    protected File rabbitJpg = new File("src/test/resources/rabbit.jpg");
    protected File rabbitPdf = new File("src/test/resources/rabbit.pdf");

    protected String callbackReceived;
    protected String callbackExpected;
    protected JsonObject payload = new JsonObject();
    protected JsonObject checkout = new JsonObject();
    protected JsonObject current_order = new JsonObject();
    private JsonObject orderId = new JsonObject();
    private JsonObject person = new JsonObject();
    private JsonObject currentOrder = new JsonObject();
    protected final double AMOUNT_14000 = 4000.0;
    private JsonObject additionalDataObject1 = new JsonObject();
    private JsonObject additionalDataObject2 = new JsonObject();
    private JsonObject cartItemsObject_1 = new JsonObject();
    private JsonObject cartItemsObject_2 = new JsonObject();
    private JsonArray cartItemsArray = new JsonArray();
    private JsonArray additionalDataArray = new JsonArray();
    private JsonObject callbackJournalObject = new JsonObject();
    private JsonObject factoringFinish = new JsonObject();
    private JsonObject factoringReturn = new JsonObject();
    private JsonObject factoringCancel = new JsonObject();
    protected JsonObject factoringChange = new JsonObject();
    private JsonObject jsonObject = new JsonObject();
    private JsonObject responsePayloadClient1 = new JsonObject();
    private JsonArray scheduleArray = new JsonArray();
    protected String iframeUrl;
    protected String jsonPayload;

    private final String generatedCyrillicUpperCase = new RandomGenerated().cyryllicUpperCase(1);
    private final String generatedCyrillicLowerCase = new RandomGenerated().cyryllicLowerCase(9);
    protected final String generatedPhoneNumber = new RandomGenerated().phoneNumber888();
    protected final String generatedPassportNumber = new RandomGenerated().number(6);
    private final String SURNAMERESPONSE = generatedCyrillicUpperCase + generatedCyrillicLowerCase + "тест";
    protected final String generatedOrderId = generated.stringValue(12);
    protected final String generatedEmail = generated.stringValue(10).toLowerCase() + "@grr.la";
    protected final String VALID_TILL = "30.12.2019 00:00:01+03:00";
    protected final int TERM_6 = 6;
    private float scheduleOverpayment;
    private JsonObject limitRequest = new JsonObject();
    protected final double AMOUNT_5000 = 5000.00;
    protected final double PREPAYMENT_AMOUNT_1000 = 1000.0;
    protected final double PREPAYMENT_AMOUNT_0 = 0;
    protected final String REDIRECT_URL = "https://bash.im";
    protected final String FIRST_NAME = "Тест";
    protected final String SURNAME = generatedCyrillicUpperCase + generatedCyrillicLowerCase;
    private JsonObject limitPhone = new JsonObject();
    protected final String PATRONYMIC = "Тестович";
    protected final String BIRTHDATE = "16.01.1930";
    protected final String DECISION_APPROVED = "approved";
    protected final String FACTORING_PRECHECK_AUTH_NO_STORE_ID = "/factoring/v1/precheck/auth?signature=";
    protected final String FACTORING_PRECHECK_AUTH_NOT_EXISTENT_STORE_ID = "/factoring/v1/precheck/auth?store_id=999999999999999999&signature=";

    @BeforeAll
    public static void setUp() throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("conf/" + System.getProperty("env") + ".properties"));
        URL                            = p.getProperty("url");
        LANDING_URL                    = p.getProperty("landing_url");
        STORE_ID = p.getProperty("store_id");
        PASSWORD                       = p.getProperty("password");
        STORE_ID_PREPAYMENT = p.getProperty("store_id_prepayment");
        PASSWORD_PREPAYMENT = p.getProperty("password_prepayment");
        STORE_ID_PREPAYMENT_LADDER = p.getProperty("store_id_prepayment_ladder");
        PASSWORD_PREPAYMENT_LADDER = p.getProperty("password_prepayment_ladder");
        ONLINE_STORE_URL               = p.getProperty("store_url");
        SMS_CODE                       = p.getProperty("sms_code");
        ADMIN_LOGIN                    = p.getProperty("admin_login");
        ADMIN_PASSWORD                 = p.getProperty("admin_password");
        MC_TRADE_OWNER                 = p.getProperty("mc_trade_owner");
        MC_TRADE_OWNER_PASSWORD        = p.getProperty("mc_trade_owner_password");
        MC_MANAGER_LOGIN               = p.getProperty("mc_manager_login");
        MC_MANAGER_PASSWORD            = p.getProperty("mc_manager_password");
        MC_MANAGER_PIN                 = p.getProperty("mc_manager_pin");
        MC_LOGIN_URL                   = p.getProperty("mc_login_url");
        SITE_URL                       = p.getProperty("site_url");

        addListener(new Highlighter());
        RestAssured.baseURI            = URL;
        Configuration.baseUrl          = URL;
        Configuration.browserSize      = "1920x1080";
        Configuration.browser          = System.getProperty("browser");
        Configuration.timeout          = 10000;
        driver = new SelenideDriver(new SelenideConfig()
                .browserSize(Configuration.browserSize)
                .holdBrowserOpen(Configuration.holdBrowserOpen)
                .headless(Configuration.headless)
                .baseUrl(Configuration.baseUrl)
                .browser(Configuration.browser));

        RestAssured.filters(new AllureRestAssured());
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
        System.setProperty("firefoxprofile.permissions.default.camera", "1");
        System.setProperty("firefoxprofile.browser.cache.disk.enable", "false");

        wireMockServer.start();
        wireMockServer.stubFor(post(anyUrl())
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("ok")));

        FACTORING_PRECHECK_AUTH   = "/auth?store_id=" + STORE_ID + "&signature=";
        FACTORING_PRECHECK_AUTH_PREPAYMENT_LADDER = "/factoring/v1/precheck/auth?store_id=" + STORE_ID_PREPAYMENT_LADDER + "&signature=";
        FACTORING_LIMIT_AUTH      = "/auth?store_id=" + STORE_ID + "&signature=";
        FACTORING_PRECHECK_FINISH = "/finish?store_id=" + STORE_ID + "&signature=";
        FACTORING_RETURN          = "/return?store_id=" + STORE_ID + "&signature=";
        FACTORING_CANCEL          = "/cancel?store_id=" + STORE_ID + "&signature=";
        FACTORING_CHANGE          = "/change/?store_id=" + STORE_ID + "&signature=";
        FACTORING_STATUS_API      = "/status?store_id=" + STORE_ID + "&signature=";
        FACTORING_STATUS_API_PREPAYMENT = "/status?store_id=" + STORE_ID_PREPAYMENT + "&signature=";
        FACTORING_STATUS_API_PREPAYMENT_LADDER = "/status?store_id=" + STORE_ID_PREPAYMENT_LADDER + "&signature=";

        LIMIT                     = "/api/external/v1/client/limit?store_id=" + STORE_ID + "&signature=";
        SCHEDULE                  = "/schedule?store_id=" + STORE_ID + "&signature=";
        CALLBACK_URL              = "http://" + CALLBACK_IP + ":" + wireMockServer.port() + "/post";
        CALLBACK_JOURNAL          = "http://" + CALLBACK_IP + ":" + wireMockServer.port() + "/__admin/requests";

        V1_LIMIT                  = "/api/external/v1/client/limit?store_id=" + STORE_ID + "&signature=";
        V1_AUTH                   = "/v1/auth?store_id=" + STORE_ID + "&signature=";
        V1_STATUS_API             = "/v1/status?store_id=" + STORE_ID + "&signature=";
        V1_RETURN                 = "/v1/return?store_id=" + STORE_ID + "&signature=";

        V2_LIMIT                  = "/api/external/v2/client/limit?store_id=" + STORE_ID + "&signature=";
        V2_AUTH                   = "/v2/auth?store_id=" + STORE_ID + "&signature=";
        V2_STATUS_API             = "/v2/status?store_id=" + STORE_ID + "&signature=";
        V2_RETURN                 = "/v2/return?store_id=" + STORE_ID + "&signature=";
    }

    @Step("Информация о платежах по тарифу")
    private JsonArray getScheduleArray(double amount) {
        jsonObject.addProperty("amount", amount);

        jsonPayload = gson.toJson(jsonObject);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post(SCHEDULE + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200)
                .body("message", is("Payload valid"),
                        "payment_schedule", hasSize(greaterThan(0)))
                .extract().response();

        ArrayList<JsonElement> jsonElement = response.path("payment_schedule.payment_dates");
        ArrayList<Float> am = response.path("payment_schedule.monthly_overpayment");
        String jsonElementGet1 = String.valueOf(jsonElement.get(1));
        scheduleOverpayment = am.get(1);
        scheduleArray = (JsonArray) jsonParser.parse(jsonElementGet1);
        return scheduleArray;
    }

    @Step("Запрос на получение ссылки айфрейма")
    protected String iframeReguest(
            String jsonPayload, String url, String password
    ) {
        return given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post(url + Encode.sha1Digest(jsonPayload + password))
                .then().log().all()
                .statusCode(200)
                .body("message", is("Payload valid"))
                .extract().path("iframe_url");
    }

    @Step("Дозаполнить айфрейм, Факторинг.")
    protected void checkIframe(
            String url,
            String email,
            String passportNumber
    ) {
        open(url);
        clearBrowserCookies();
        clearBrowserLocalStorage();
        assertThat($("#online_loan_request_factoring_client_human_attributes_first_name").getValue(), is(FIRST_NAME));
        assertThat($("#online_loan_request_factoring_client_human_attributes_surname").getValue(), is(SURNAME + "тест"));
        assertThat($("#online_loan_request_factoring_client_human_attributes_patronymic").getValue(), is(PATRONYMIC));
        assertThat($("#online_loan_request_factoring_client_birth_date").getValue(), is(BIRTHDATE));
        assertThat($("#online_loan_request_factoring_client_email").getValue(), is(email));
        $("#online_loan_request_factoring_client_passport_series_and_number").sendKeys(Keys.BACK_SPACE + "1108" + passportNumber);
        $("#submit-form").click();
        $("#online_loan_request_factoring_confirmation_bki_confirmation_code_confirmation").sendKeys(SMS_CODE); //1111 on staging, 7777 on prod.
        $(".formFooter__button").click();
        $(".linkButton").click();
        $("body > div.popup > div.formFooter > span");
    }

    @Step("Дозаполнить айфрейм, V1.")
    protected void checkIframeV1(
            String url,
            String email,
            String passportNumber
    ) {
        open(url);
        clearBrowserCookies();
        clearBrowserLocalStorage();
        assertThat($("#online_loan_request_full_client_human_attributes_first_name").getValue(), is(FIRST_NAME));
        assertThat($("#online_loan_request_full_client_human_attributes_surname").getValue(), is(SURNAME));
        assertThat($("#online_loan_request_full_client_human_attributes_patronymic").getValue(), is(PATRONYMIC));
        assertThat($("#online_loan_request_full_client_birth_date").getValue(), is(BIRTHDATE));
        assertThat($("#online_loan_request_full_client_email").getValue(), is(email));
        $(byText("Серия и номер паспорта")).click();
        $("#online_loan_request_full_client_passport_series_and_number").sendKeys(Keys.BACK_SPACE + "1108" + passportNumber);
        $(byText("Дата выдачи")).click();
        $("#online_loan_request_full_client_passport_issue_date").sendKeys("01.01.2008");
        $(byText("Кем выдан")).click();
        $("#online_loan_request_full_client_passport_issued_by").sendKeys("ЗАО Рога и копыта");
        $(byText("Ваша улица или переулок")).click();
        $("#online_loan_request_full_client_address_attributes_street").sendKeys("Ленина");
        $(byText("Ваш город или населенный пункт")).click();
        $("#online_loan_request_full_client_address_attributes_settlement").sendKeys("Москва г.");
        $(byText("Ваша область или регион")).click();
        $("#online_loan_request_full_client_address_attributes_area").sendKeys("Москва г.");
        $(byText("Дом")).click();
        $("#online_loan_request_full_client_address_attributes_house").sendKeys("1");
        $(byText("Здание")).click();
        $("#online_loan_request_full_client_address_attributes_building").sendKeys("2");
        $(byText("Квартира")).click();
        $("#online_loan_request_full_client_address_attributes_apartment").sendKeys("3");
        $(byText("СМС-код")).click();
        $("#online_loan_request_full_client_bki_confirmation_code_confirmation").sendKeys(SMS_CODE);
        $(".fieldContainer__checkboxIcon").click();
        $("#submit-form").click();
        $("#online_loan_request_documents_name").uploadFile(rabbitJpg);
        $("#online_loan_request_documents_client_with_passport").uploadFile(rabbitJpg);
        $("#submit-docs").click();
        $("#new_online_loan_request_offer > div:nth-child(7) > label > span").click();
        $(byText("СМС-код")).click();
        $("#online_loan_request_offer_bki_confirmation_code_confirmation").sendKeys(SMS_CODE);
        $("div.formBlock.formBlock--continue > input").click();
        $(byText("Вернуться в магазин")).click();
    }

    @Step("Деактивация клиента через админку.")
    protected void webAdminLoanBlock(String phoneNumber) {
        open("/admin");
        $("#user_login").setValue(ADMIN_LOGIN);
        $("#user_password").setValue(ADMIN_PASSWORD);
        $(".submit").click();
        open("/admin/clients");
        $("#q_mobile_phone").setValue(phoneNumber);
        $(".buttons > input:nth-child(1)").click();
        $("a.member_link:nth-child(1)").click();
        $("span.action_item:nth-child(7) > a:nth-child(1)").click();
    }

    @Step("Дозаполнить айфрейм, V2.")
    protected void checkIframeV2(
            String url,
            String email,
            String passportNumber
    ) {
        open(url);
        clearBrowserCookies();
        clearBrowserLocalStorage();
        assertThat($("#online_loan_request_factoring_client_human_attributes_first_name").getValue(), is(FIRST_NAME));
        assertThat($("#online_loan_request_factoring_client_human_attributes_surname").getValue(), is(SURNAME + "тест"));
        assertThat($("#online_loan_request_factoring_client_human_attributes_patronymic").getValue(), is(PATRONYMIC));
        assertThat($("#online_loan_request_factoring_client_birth_date").getValue(), is(BIRTHDATE));
        assertThat($("#online_loan_request_factoring_client_email").getValue(), is(email));
        $("#online_loan_request_factoring_client_passport_series_and_number").sendKeys(Keys.BACK_SPACE + "1108" + passportNumber);
        $("#submit-form").click();
        $("#online_loan_request_factoring_confirmation_bki_confirmation_code_confirmation").sendKeys(SMS_CODE); //1111 on staging, 7777 on prod.
        $(".formFooter__button").click();
        $("#online_loan_request_cart_name").uploadFile(rabbitJpg);
        $("#online_loan_request_cart_client_with_passport").uploadFile(rabbitJpg);
        $("#online_loan_request_cart_living_addr").uploadFile(rabbitJpg);
        $("#online_loan_request_cart_bki_confirmation_code_confirmation").click();
        $("#online_loan_request_cart_bki_confirmation_code_confirmation").sendKeys(SMS_CODE);
        $("#new_online_loan_request_cart > div.form__footer.formFooter > input").click();
        $("body > div.popup > div.formFooter > span").click();
    }

    @Step("Проверка статуса займа факторинг")
    protected void factoringStatusCheck(String current_order, String expectedStatus) {

        orderId.addProperty("order_id", current_order);

        jsonPayload = gson.toJson(orderId);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post(FACTORING_STATUS_API + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("current_order.status", is(expectedStatus));
    }

    @Step("Проверка статуса заявки Факторинг с лестницей предоплат")
    protected void factoringPrepaymentLadderStatusCheck(String current_order, String expectedStatus) {
        orderId.addProperty("order_id", current_order);

        jsonPayload = gson.toJson(orderId);

        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post(FACTORING_STATUS_API_PREPAYMENT_LADDER + Encode.sha1Digest(jsonPayload + PASSWORD_PREPAYMENT_LADDER))
                .then().log().all()
                .statusCode(200).body("current_order.status", is(expectedStatus));
    }

    @Step("Финализация заявки, отправка чека PDF")
    protected void factoringFinish(String orderId, Double amount) {
        String factoringFinishJsonPayload = constructFactoringFinishJSON(orderId, amount);

        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("body", factoringFinishJsonPayload)
                .multiPart("check", rabbitPdf)
                .when().log().all()
                .post(FACTORING_PRECHECK_FINISH + Encode.sha1Digest(factoringFinishJsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is("Payload valid"));
    }

    @Step("Финализация заявки, отправка чека PDF")
    protected void factoringFinish(String jsonPayload) {
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("body", jsonPayload)
                .multiPart("check", rabbitPdf)
                .when().log().all()
                .post(FACTORING_PRECHECK_FINISH + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is("Payload valid"));
    }

    @Step("Возврат займа факторинга")
    protected void factoringReturn(String orderId, Double amount) {
        given()
                .header("Content-Type", "application/json")
                .body(constructFactoringReturnJSON(orderId, amount))
                .when().log().all()
                .post(FACTORING_RETURN + Encode.sha1Digest(constructFactoringReturnJSON(orderId, amount) + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is("Payload valid"));
    }

    @Step("Изменение суммы займа факторинг")
    protected void factoringChange(String orderId, Double amount) {
        String factoringChangeJsonPayload = constructFactoringChangeJSON(orderId, amount);

        given()
                .header("Content-Type", "application/json")
                .body(factoringChangeJsonPayload)
                .when().log().all()
                .post(FACTORING_CHANGE + Encode.sha1Digest(factoringChangeJsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is("Payload valid"));
    }

    @Step("Отмена займа факторинг")
    protected void factoringCancel(String orderId) {
        given()
                .header("Content-Type", "application/json")
                .body(constructFactoringCancelJSON(orderId))
                .when().log().all()
                .post(FACTORING_CANCEL + Encode.sha1Digest(constructFactoringCancelJSON(orderId) + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is("Payload valid"));
    }

    @Step("Кастомный запрос для проверки ошибок. Финализация.")
    protected void customFactoringFinish(String orderId, Double amount, String url, String jsonPayload, String expectedMessage) {
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("body", constructFactoringFinishJSON(orderId, amount))
                .multiPart("check", rabbitPdf)
                .when().log().all()
                .post(url + Encode.sha1Digest(constructFactoringFinishJSON(orderId, amount) + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is(expectedMessage));
    }

    @Step("Кастомный запрос для проверки ошибок. Финализация.")
    protected void customFactoringFinish(String jsonPayload, String url, String expectedMessage) {
        given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("body", jsonPayload)
                .multiPart("check", rabbitPdf)
                .when().log().all()
                .post(url + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is(expectedMessage));
    }

    @Step("Кастомный запрос для проверки ошибок. Изменение суммы займа")
    protected void customFactoringChangeOrCancel(String jsonPayload, String url, String expectedMessage) {
        given()
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .when().log().all()
                .post(url + Encode.sha1Digest(jsonPayload + PASSWORD))
                .then().log().all()
                .statusCode(200).body("message", is(expectedMessage));
    }

    @Step("Узнать лимит")
    protected void getLimit(String phoneNumber, String jsonPayload, String clientStatus) {
        given()
                .header("Content-Type", "application/json")
                .body(constructLimitRequestJSON(phoneNumber))
                .when().log().all()
                .post(LIMIT + Encode.sha1Digest(constructLimitRequestJSON(phoneNumber) + PASSWORD))
                .then().log().all()
                .statusCode(200)
                .body("meta.message", is("Payload valid"),
                        "client.status", is(clientStatus),
                        "client.limit_amount", is("10000.00"),
                        "client.mobile_phone", is(phoneNumber));
    }

    @Step("Проверка статуса заявки V1")
    protected void v12StatusCheck(String current_order, String expectedStatus) {
        orderId.addProperty("order_id", current_order);

        given()
                .header("Content-Type", "application/json")
                .body(gson.toJson(orderId))
                .when().log().all()
                .post(FACTORING_STATUS_API + Encode.sha1Digest(gson.toJson(orderId) + PASSWORD))
                .then().log().all()
                .statusCode(200).body("current_order.status", is(expectedStatus));
    }

    @Step("Сгенерировать ожидаемый Callback")
    protected String generateCallbackExpected(
            String orderId,
            String decision,
            double amount,
            int term,
            String phone,
            String email
    ) {
        scheduleArray = getScheduleArray(amount);

        callbackJournalObject.addProperty("order_id", orderId);
        callbackJournalObject.addProperty("decision", decision);
        callbackJournalObject.addProperty("amount", amount);
        callbackJournalObject.addProperty("term", term);
        callbackJournalObject.add("client", responsePayloadClient1);
        responsePayloadClient1.addProperty("primary_phone", phone);
        responsePayloadClient1.addProperty("full_name", SURNAMERESPONSE + " " + FIRST_NAME + " " + PATRONYMIC);
        responsePayloadClient1.addProperty("first_name", FIRST_NAME);
        responsePayloadClient1.addProperty("surname", SURNAMERESPONSE);
        responsePayloadClient1.addProperty("patronymic", PATRONYMIC);
        callbackJournalObject.add("schedule", scheduleArray);
        callbackJournalObject.addProperty("monthly_overpayment", scheduleOverpayment);
        callbackJournalObject.addProperty("email", email);

        String json = callbackJournalObject.toString();
        byte[] jsonToBytes = json.getBytes();
        byte[] encodedJson = decoder.encode(jsonToBytes);
        String jsonString = new String(encodedJson);
        List<String> arrayList = new ArrayList<>();
        arrayList.add(jsonString);
        String string = arrayList.toString();
        return string.substring(1, string.length()-1);
    }

    @Step("Сгенерировать ожидаемый Callback с учетом предоплаты.")
    protected String generateCallbackExpectedPrepayment(
            String orderId,
            String decision,
            double amount,
            double prepayment_amount,
            double total_amount,
            int term,
            String phone,
            String email
    ) {
        callbackJournalObject.addProperty("order_id", orderId);
        callbackJournalObject.addProperty("decision", decision);
        callbackJournalObject.addProperty("amount", amount);
        callbackJournalObject.addProperty("prepayment_amount", prepayment_amount);
        callbackJournalObject.addProperty("total_amount", total_amount);
        callbackJournalObject.addProperty("term", term);
        callbackJournalObject.add("client", responsePayloadClient1);
        responsePayloadClient1.addProperty("primary_phone", phone);
        responsePayloadClient1.addProperty("full_name", SURNAMERESPONSE + " " + FIRST_NAME + " " + PATRONYMIC);
        responsePayloadClient1.addProperty("first_name", FIRST_NAME);
        responsePayloadClient1.addProperty("surname", SURNAMERESPONSE);
        responsePayloadClient1.addProperty("patronymic", PATRONYMIC);
        callbackJournalObject.add("schedule", scheduleArray);
        callbackJournalObject.addProperty("monthly_overpayment", scheduleOverpayment);
        callbackJournalObject.addProperty("email", email);

        String json = callbackJournalObject.toString();
        byte[] jsonToBytes = json.getBytes();
        byte[] encodedJson = decoder.encode(jsonToBytes);
        String jsonString = new String(encodedJson);
        List<String> arrayList = new ArrayList<>();
        arrayList.add(jsonString);
        String string = arrayList.toString();
        return string.substring(1, string.length()-1);
    }

    @Step("Запрос на локальный вебсервер, в ожидании коллбэка.")
    protected String callbackReceive() {
        Response response = given()
                .header("Content-Type", "application/json")
                .when().log().all()
                .get(CALLBACK_JOURNAL);
        List<String> str = response.path("requests.request.bodyAsBase64");
        return str.toString();
    }

    @Step("Формирование большого JSON")
    protected String constructMaximalJSON(
            String orderId,
            double amount,
            int term,
            String phone,
            String email
    ) {
        payload.addProperty("callback_url", CALLBACK_URL);
        payload.addProperty("redirect_url", REDIRECT_URL);
        payload.addProperty("primary_phone", phone);
        payload.addProperty("primary_email", email);
        current_order.addProperty("order_id", orderId);
        current_order.addProperty("valid_till", VALID_TILL);
        current_order.addProperty("term", term);
        current_order.addProperty("amount", amount);
        payload.add("current_order", current_order);
        person.addProperty("first_name", FIRST_NAME);
        person.addProperty("surname", SURNAME);
        person.addProperty("patronymic", PATRONYMIC);
        person.addProperty("birth_date", BIRTHDATE);
        payload.add("person", person);
        cartItemsObject_1.addProperty("title", "Title_one");
        cartItemsObject_1.addProperty("sky", 1231);
        cartItemsObject_1.addProperty("name", "Samsung Note 8");
        cartItemsObject_1.addProperty("price", 14000.0);
        cartItemsObject_1.addProperty("quantity", 1);
        cartItemsObject_1.addProperty("brand", "Samsung");
        cartItemsObject_1.addProperty("category", "Smartphone");
        cartItemsArray.add(cartItemsObject_1);
        cartItemsObject_2.addProperty("title", "Title_second");
        cartItemsObject_2.addProperty("sku", 23543);
        cartItemsObject_2.addProperty("name", "Чехол фирменный");
        cartItemsObject_2.addProperty("price", 3500.0);
        cartItemsObject_2.addProperty("sale_price", 1000.0);
        cartItemsObject_2.addProperty("quantity", 1);
        cartItemsObject_2.addProperty("brand", "Samsung");
        cartItemsObject_2.addProperty("phew", "Phew");
        cartItemsArray.add(cartItemsObject_2);
        payload.add("cart_items", cartItemsArray);
        payload.addProperty("skip_result_page", true);
        additionalDataObject1.addProperty("name", "Color");
        additionalDataObject1.addProperty("value", "Black");
        additionalDataObject2.addProperty("name", "Size");
        additionalDataObject2.addProperty("value", "Large");
        additionalDataArray.add(additionalDataObject1);
        additionalDataArray.add(additionalDataObject2);
        payload.add("additional_data", additionalDataArray);

        jsonPayload = gson.toJson(payload);
        return jsonPayload;
    }

    @Step("Формирование малого JSON")
    protected String constructMinimalJSON(
            String orderId,
            double amount,
            double prepaymentAmount,
            int term,
            String phone,
            String email
    ) {
        payload.add("current_order", currentOrder);
        payload.addProperty("primary_phone", phone);
        payload.addProperty("callback_url", CALLBACK_URL);
        payload.addProperty("redirect_url", REDIRECT_URL);
        payload.addProperty("primary_email", email);
        currentOrder.addProperty("order_id", orderId);
        currentOrder.addProperty("amount", amount);
        currentOrder.addProperty("prepayment_amount", prepaymentAmount);
        currentOrder.addProperty("term", term);
        currentOrder.addProperty("valid_till", VALID_TILL);
        person.addProperty("first_name", FIRST_NAME);
        person.addProperty("surname", SURNAME);
        person.addProperty("patronymic", PATRONYMIC);
        person.addProperty("birth_date", BIRTHDATE);
        payload.add("person", person);

        jsonPayload = gson.toJson(payload);
        return jsonPayload;
    }

    @Step("Логин в оффлайн-форму")
    protected void offlineFormLogin(String login, String password) {
        open("/form/login");
        screenshot("login");
        dataId("login-page__input--phone").setValue(login);
        screenshot("setValueLogin");
        dataId("login-page__input--password").setValue(password);
        screenshot("setValuePassword");
        dataId("login-page__button--submit").click();
        screenshot("pressSubmit");

/*
        $("#mobile_phone").setValue(login);
        $("#password").setValue(password);
        $("input.btn").click();*/
    }

    @Step("Формирование малого JSON для V1")
    protected String constructV1MinimalJSON(
            String orderId,
            String sum,
            int term,
            String phone,
            String email
    ) {
        payload.add("current_order", currentOrder);
        payload.addProperty("primary_phone", phone);
        payload.addProperty("callback_url", CALLBACK_URL);
        payload.addProperty("redirect_url", REDIRECT_URL);
        payload.addProperty("primary_email", email);
        currentOrder.addProperty("order_id", orderId);
        currentOrder.addProperty("term", term);
        currentOrder.addProperty("sum", sum);
        //currentOrder.addProperty("valid_till", VALID_TILL);
        person.addProperty("first_name", FIRST_NAME);
        person.addProperty("surname", SURNAME);
        person.addProperty("patronymic", PATRONYMIC);
        person.addProperty("birth_date", BIRTHDATE);
        payload.add("person", person);

        jsonPayload = gson.toJson(payload);
        return jsonPayload;
    }

    @Step("Формирование JSON для запроса на лимит")
    protected String constructLimitRequestJSON(String phoneNumber) {
        limitRequest.add("client", limitPhone);
        limitPhone.addProperty("mobile_phone", phoneNumber);
        jsonPayload = gson.toJson(limitRequest);
        return jsonPayload;
    }

    @Step("Формирование JSON для возврата по V1 и V2")
    protected String constructV12ReturnJSON(
            String orderId,
            String sum
    ){
        JsonObject v1Return = new JsonObject();
        v1Return.addProperty("order_id", orderId);
        v1Return.addProperty("sum", sum);

        jsonPayload = gson.toJson(v1Return);
        return jsonPayload;
    }

    @Step("Формирование JSON для изменения содержимого корзины.")
    protected String constructFactoringChangeJSON(
            String orderId,
            double amount
    ){
        factoringChange.addProperty("order_id", orderId);
        factoringChange.addProperty("amount", amount);
        cartItemsObject_1.addProperty("name", "Samsung Note 8");
        cartItemsObject_1.addProperty("price", "55999");
        cartItemsObject_1.addProperty("quantity", "1");
        cartItemsArray.add(cartItemsObject_1);
        factoringChange.add("cart_items", cartItemsArray);

        jsonPayload = gson.toJson(factoringChange);
        return jsonPayload;
    }

    @Step("Формирование JSON для финализации займа факторинга")
    protected String constructFactoringFinishJSON(
            String orderId,
            double amount
    ) {
        factoringFinish.addProperty("order_id", orderId);
        factoringFinish.addProperty("amount", amount);
        factoringFinish.addProperty("check_number", orderId + generatedEmail);

        jsonPayload = gson.toJson(factoringFinish);
        return jsonPayload;
    }

    @Step("Формирование JSON для отмены займа факторинга")
    private String constructFactoringCancelJSON(
            String orderId
    ) {
        factoringCancel.addProperty("order_id", orderId);

        jsonPayload = gson.toJson(factoringCancel);
        return jsonPayload;
    }

    /*@Step("Очистка после тестов")
    private static void killSidekiqRetries() {
        open("/admin");
        $("#user_login").setValue(ADMIN_LOGIN);
        $("#user_password").setValue(ADMIN_PASSWORD);
        $(".submit").click();
        open("/admin/sidekiq/retries");
        $(byValue("Kill all")).click();
        switchTo().alert().accept();
    }*/

    @Step("Формирование JSON для возврата займа факторинга")
    private String constructFactoringReturnJSON(
            String orderId,
            double amount
    ){
        factoringReturn.addProperty("order_id", orderId);
        factoringReturn.addProperty("amount", amount);

        jsonPayload = gson.toJson(factoringReturn);
        return jsonPayload;
    }

    private SelenideElement dataId(String dataTestId){
        return $(byAttribute("data-test-id", dataTestId));
    }
  
    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
        //killSidekiqRetries();
    }
}
