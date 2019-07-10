package gui;

import core.TestBase;
import org.junit.jupiter.api.RepeatedTest;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;

class Search extends TestBase {
    @RepeatedTest(10)
    void google() {
//        showResults("Капибара").shouldHave(sizeGreaterThan(1));
    }
}
