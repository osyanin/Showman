package gui;

import core.TestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.*;

class Landings extends TestBase {
    @ParameterizedTest
    @CsvFileSource(resources = "/landings.csv")
    void list(String name, String text) {
        open(LANDING_URL+name);
        $(byValue(text)).isDisplayed();
        screenshot(name);
    }
}
