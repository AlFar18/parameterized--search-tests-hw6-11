package litres;

import com.codeborne.selenide.Condition;
import litres.domain.MenuItem;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
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
        $(".Search-module__input").setValue(testData).pressEnter();
        $(".result_container").shouldHave(text(testData));
    }

    @CsvSource(value = {
            "Таинственная история Билли Миллигана| Автор: Дэниел Киз",
            "Американская трагедия| Автор: Теодор Драйзер"
    }, delimiter = '|')
    @ParameterizedTest(name = "Проверка поисковых результатов запроса\"{0}\"")
    void complexSearchTest(String testData, String expectedText) {
        $(".Search-module__input").setValue(testData).pressEnter();
        $(".result_container").shouldHave(text(expectedText));
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
        $(".Search-module__input").setValue(firstArg).pressEnter();
        $(".result_container").shouldHave(text(firstArg));
        $(".result_container").shouldHave(text(secondArg));
    }

    @EnumSource(MenuItem.class)
    @ParameterizedTest()
    void commonSearchMenuTest(MenuItem testData) {
        $(".Search-module__input").setValue("Американская трагедия").pressEnter();
        $$("#result-tabs li")
                .find(Condition.text(testData.rusName))
                .click();
        $(".tab-active").shouldHave(text(testData.rusName));
    }
}
