import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

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

}
