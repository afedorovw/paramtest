package Utils;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class ConfigFile {

    @BeforeAll
    static void configure() {
        Configuration.browser = "chrome"; //edge
        Configuration.browserSize = "1920x1080";
    }
}
