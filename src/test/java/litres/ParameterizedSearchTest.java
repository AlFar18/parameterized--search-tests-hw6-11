package litres;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedSearchTest {

    @BeforeEach
    void precondition() {
        open("https://www.litres.ru/");
    }

    @AfterEach
    void closeBrowser() {
        closeWebDriver();
    }

    @ValueSource(strings = {"Таинственная история Билли Миллигана", "Американская трагедия"})
    @ParameterizedTest(name = "Проверка отображения поисковых результатов запроса \"{0}\"")
    void commonSearchTest(String testData) {
        $("[class=Search-module__input]").setValue(testData).pressEnter();
        $(".b_search").shouldHave(text(testData)).shouldBe(visible);
    }

    @CsvSource(value = {
            "Таинственная история Билли Миллигана| Автор: Дэниел Киз",
            "Американская трагедия| Автор: Теодор Драйзер"
    }, delimiter = '|')
    @ParameterizedTest(name = "Проверка поисковых результатов запроса\"{0}\"")
    void complexSearchTest(String testData, String expectedText) {
        $("[class=Search-module__input]").setValue(testData).pressEnter();
        $(".b_search").shouldHave(text(expectedText)).shouldBe(visible);
    }

    static Stream<Arguments> mixedArgumentsTestDataProvider() {
        return Stream.of(
                Arguments.of("Таинственная история Билли Миллигана", "Автор: Дэниел Киз"),
                Arguments.of("Американская трагедия", "Автор: Теодор Драйзер")
        );
    }

    @MethodSource(value = "mixedArgumentsTestDataProvider")
    @ParameterizedTest(name = "Проверка поисковых результатов запроса \"{0}\"")
    void mixedArgumentsTest(String firstArg, String secondArg) {
        $("[class=Search-module__input]").setValue(firstArg).pressEnter();
        $(".b_search").shouldHave(text(firstArg)).shouldBe(visible);
        $(".b_search").shouldHave(text(secondArg)).shouldBe(visible);
    }
}
