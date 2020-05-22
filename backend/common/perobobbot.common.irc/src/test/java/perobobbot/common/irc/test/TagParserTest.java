package perobobbot.common.irc.test;

import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.common.irc.Tag;
import perococco.perobobbot.common.irc.TagParser;

import java.util.stream.Stream;

/**
 * @author perococco
 **/
public class TagParserTest {

    public static Stream<TagParsingParameter> validTags() {
        return Stream.of(
                TagParsingParameter.create("+perococco.com/server-time=123\\:445", true, "perococco.com", "server-time", "123;445"),
                TagParsingParameter.create("color=#1E90FF", false, null, "color", "#1E90FF"),
                TagParsingParameter.create("site.com/tag-as=two\\svalues\\:=result°", false, "site.com", "tag-as", "two values;=result°")
                );
    }
    public static Stream<String> invalidTags() {
        return Stream.of(
                "+perococco.com/server-time=",
                "=#1E90FF",
                "site.com/tag-astwo\\svalues\\:esult°"
                );
    }

    private TagParser tagParser;

    @BeforeEach
    public void setUp() {
        tagParser = new TagParser();
    }

    @ParameterizedTest
    @MethodSource("validTags")
    public void hasRightClientPrefix(@NonNull TagParsingParameter parameter) {
        final Tag tag = parseTag(parameter);
        Assertions.assertEquals(parameter.expectedTag.isClient(), tag.isClient());
    }

    @ParameterizedTest
    @MethodSource("validTags")
    public void hasRightVendor(@NonNull TagParsingParameter parameter) {
        final Tag tag = parseTag(parameter);
        Assertions.assertEquals(parameter.expectedTag.vendor(),tag.vendor());
    }

    @ParameterizedTest
    @MethodSource("validTags")
    public void hasRightKeyName(@NonNull TagParsingParameter parameter) {
        final Tag tag = parseTag(parameter);
        Assertions.assertEquals(parameter.expectedTag.getKeyName(),tag.getKeyName());
    }

    @ParameterizedTest
    @MethodSource("validTags")
    public void hasRightValue(@NonNull TagParsingParameter parameter) {
        final Tag tag = parseTag(parameter);
        Assertions.assertEquals(parameter.expectedTag.getValue(),tag.getValue());
    }


    @ParameterizedTest
    @MethodSource("invalidTags")
    public void isInvalidTag(@NonNull String tagAsString) {
        final Tag tag = tagParser.parse(tagAsString).orElse(null);
        Assertions.assertNull(tag);
    }

    @NonNull
    private Tag parseTag(@NonNull TagParsingParameter parameter) {
        final String tagAsString = parameter.getTagAsString();
        final Tag tag = tagParser.parse(tagAsString).orElse(null);
        Assertions.assertNotNull(tag);
        return tag;
    }

    @Value
    public static class TagParsingParameter {

        public static TagParsingParameter create(@NonNull String tagAsString, boolean client, String vendor, String keyName, String value) {
            return new TagParsingParameter(tagAsString,Tag.builder().client(client).vendor(vendor).keyName(keyName).value(value).build());
        }

        @NonNull
        private final String tagAsString;

        @NonNull
        private final Tag expectedTag;

    }
}
