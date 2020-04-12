package perobobbot.bot.common.irc;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author perococco
 **/
public class TagValueUnescaperTest {

    @NonNull
    public static Object[][] escapedValues() {
        return new Object[][]{
                {"\\r","\r"},
                {"\\n","\n"},
                {"\\:",";"},
                {"\\s"," "},
                {"\\\\","\\"},
                {"\\asd","asd"},
                {"hello\\","hello"},
                {"coucou","coucou"},
                {"Coucou\\s\\:\\sHello\\s\\n","Coucou ; Hello \n"}
        };
    }

    @ParameterizedTest
    @MethodSource("escapedValues")
    public void shouldEscapeTo(@NonNull String escapedValue,@NonNull String expectedValue) {
        final TagValueUnescaper unescaper = new TagValueUnescaper();
        final String actualValue = unescaper.unescape(escapedValue);
        Assertions.assertEquals(expectedValue,actualValue);
    }

}
