import Utils.ConfigFile;
import com.codeborne.selenide.CollectionCondition;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ParamTest extends ConfigFile {

    @ValueSource(strings = {"Альфа-Банк", "Тинькофф"})
    @ParameterizedTest(name = "Поиск группы {0} и переход в нее")
    void searchGroupTest(String testData) {
        open("https://vk.com");
        $("#ts_input").setValue(testData).pressEnter();
        $$(".groups_row").first().shouldHave(text(testData));
        $(".page_verified").shouldBe(visible);
        $$(".groups_row").first().click();
        $("#page_wall_posts").shouldHave(text(testData));
    }

    @CsvSource(value = {
            "Альфа-Банк, Лучший мобильный банк.",
            "Тинькофф, Он такой один",
    })

    @ParameterizedTest(name = "Поиск группы {0} и переход в нее")
    void searchOpenGroupTest(String testData) {
        open("https://vk.com");
        $("#ts_input").setValue(testData).pressEnter();
        $$(".groups_row").first().shouldHave(text(testData));
        $(".page_verified").shouldBe(visible);
        $$(".groups_row").first().click();
        $("#page_wall_posts").shouldHave(text(testData));
    }

    static Stream<Arguments> dataProviderForVKSearch() {
        return Stream.of(
                Arguments.of(Lang.Русский, List.of("О ВКонтакте", "Правила", "Для бизнеса", "Разработчикам")),
                Arguments.of(Lang.English, List.of("About", "VK Terms", "Developers"))
        );
    }

    @MethodSource("dataProviderForVKSearch")
    @ParameterizedTest(name = "Для локали {0} отображаются кнопки {1}")
    void selenideSiteMenuTest(Lang lang, List<String> expectedButtons) {
        open("https://vk.com/");
        $$(".footer_lang a").find(text(lang.name())).click();
        $$(".footer_links a")
                .filter(visible)
                .shouldHave(CollectionCondition.texts(expectedButtons));
    }

    @EnumSource(Lang.class)
    @ParameterizedTest(name = "Для локали {0} отображается главный логотип")
    void selenideSiteMenuEnumTest(Lang lang) {
        open("https://vk.com/");
        $$(".footer_lang a").find(text(lang.name())).click();
        $(".VkIdForm__icon").shouldBe(visible);
    }

}
