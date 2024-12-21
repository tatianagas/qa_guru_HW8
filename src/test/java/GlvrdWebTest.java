import data.CheckTab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class GlvrdWebTest {

    @BeforeEach
    void setUp() {
        open("https://glvrd.ru/");
    }

    @CsvSource(value = {
            "Хороший текст; 1,2",
            "Так себе текст; 10"
    }, delimiter = ';'
    )

    @ParameterizedTest(name = "Для текста {0} должен быть получен балл {1}")
    @Tag("WEB")
    void textPoint(String text, String expectedPoint) {
        $("#glaveditor-id .ql-editor").setValue(text);
        $(".stats-score").shouldHave(text(expectedPoint));
    }


    @ValueSource(strings = {
            "Нет стоп-слов", "Без стоп-слов", "Совсем без слов"
    })
    @ParameterizedTest(name = "Для текста {0} не должно быть найдено ни одного стоп-слова")
    @Tag("BLOKER")
    void textWithoutStopword(String testText){
        $("#glaveditor-id .ql-editor").setValue(testText);
        $(".stats-result-div").shouldHave(text("0 стоп-слов."));
    }





    static Stream<Arguments> successTextCheck() {
        return Stream.of(
                Arguments.of(
                        CheckTab.PURITY,
                        "Удивительные вещи происходят с теми, кто готов их принимать.",
                        List.of(
                                "10 баллов из 10 по шкале Главреда",
                                "1 предложение 9 слов, 61 знак",
                                "1 стоп-слово. Основная проблема: личное местоимение"
                        )
                ),
                Arguments.of(
                        CheckTab.READABILITY,
                        "Удивительные вещи происходят с теми, кто готов их принимать.",
                        List.of(
                                "10 баллов из 10 по шкале Главреда",
                                "1 предложение 9 слов, 61 знак",
                                "0 стоп-слов."
                        )
                )
        );
    }

    @MethodSource
    @ParameterizedTest
    @Tag("WEB")
    void successTextCheck(CheckTab checkTab, String text, List<String> expectedParam) {
        $$(".tab").find(text(checkTab.description)).click();
        $("#glaveditor-id .ql-editor").setValue(text);
        $$(".stats div").shouldHave(texts(expectedParam));

    }


}
