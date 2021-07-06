package perobobbot.server.plugin.template;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.template.StructureEntryParser;

import java.util.stream.Stream;

public class TestStructureEntry {

    public static @NonNull Stream<Arguments> structureEntrySample() {
        return Stream.of(
                Arguments.of("common: dot_gitignore .gitignore", ".gitignore"),
                Arguments.of("common: dot_gitignore    .gitignore", ".gitignore"),
                Arguments.of("common: dot_gitignore    toto", "toto")
        );
    }

    @ParameterizedTest
    @MethodSource("structureEntrySample")
    public void shouldHaveRightTemplateFileName(@NonNull String line, @NonNull String expectedTemplateFileName) {
        final var entry = StructureEntryParser.parse(line);
        Assertions.assertEquals(expectedTemplateFileName, entry.getTemplatePath());
    }
}
