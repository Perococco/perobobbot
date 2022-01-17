package perobobbot.command;

import lombok.NonNull;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perococco.command.CommandDefinitionParser;
import perococco.command.CommandRegexpParser;
import perococco.command.FullMatchCommandDefinitionParser;

import java.util.stream.Stream;

public class CommandRegexpParserTest {

    private static @NonNull String requiredParameter(@NonNull String name) {
        return requiredParameter(name,false);
    }
    private static @NonNull String optionalParameter(@NonNull String name) {
        return optionalParameter(name,false);
    }

    private static @NonNull String requiredParameter(@NonNull String name,boolean noSpace) {
        final var base ="(?<"+name+">"+ CommandRegexpParser.ARGUMENT_PATTERN+")";
        return noSpace?base:"\s+"+base;
    }
    private static @NonNull String optionalParameter(@NonNull String name,boolean noSpace) {
        final var base ="(?<"+name+">"+ CommandRegexpParser.ARGUMENT_PATTERN+")";
        return "("+(noSpace?base:"\s+"+base)+")?";
    }

    public static Stream<Arguments> validSamples() {
        return Stream.of(
                Arguments.of("play?", "play?",0, "^play\\?$"),
                Arguments.of("decho*", "decho",0, "^decho( .*)?$"),
                Arguments.of("repeat*", "repeat",0, "^repeat( .*)?$"),
                Arguments.of("play stop", "play|stop",0, "^play\s+stop$"),
                Arguments.of("play {title}", "play", 1,"^play("+requiredParameter("title")+")$"),
                Arguments.of("play {title} [volume]","play",2,"^play("+requiredParameter("title")+optionalParameter("volume")+")$"),
                Arguments.of("em list","em|list",0,"^em\s+list$"),
                Arguments.of("em enable {extension}","em|enable",1,"^em\s+enable("+requiredParameter("extension")+")$"),
                Arguments.of("em.new enable {extension}","em.new|enable",1,"^em\\.new\s+enable("+requiredParameter("extension")+")$"),
                Arguments.of("play {x},{y} [arg3]","play",3,"^play("+requiredParameter("x")+","+requiredParameter("y",true)+optionalParameter("arg3")+")$")
        );
    }

    public static Stream<String> invalidSamples() {
        return Stream.of(
                "play {u a}",
                "u&",
                "play {title} {volum e}"
        );
    }

    private CommandDefinitionParser parser;

    @BeforeEach
    public void setUp() {
        parser = CommandDefinitionParser.chain(FullMatchCommandDefinitionParser.create(), CommandRegexpParser.create());
    }

    @ParameterizedTest
    @MethodSource("invalidSamples")
    public void shouldFail(@NonNull String definition) {
        Assertions.assertThrows(CommandDefinitionParsingFailure.class,() -> parser.parse(definition));
    }

    @ParameterizedTest
    @MethodSource("validSamples")
    public void shouldHaveRightRegexp(@NonNull String definition, @NonNull @AggregateWith(ParsingAggregator.class) Parsing expected) {
        final var actual = parser.parse(definition);
        Assertions.assertEquals(expected.getRegexp(),actual.getRegexp());
    }

    @ParameterizedTest
    @MethodSource("validSamples")
    public void shouldHaveRightFullCommand(@NonNull String definition, @NonNull @AggregateWith(ParsingAggregator.class) Parsing expected) {
        final var actual = parser.parse(definition);
        Assertions.assertEquals(expected.getFullCommand(),actual.getFullCommand());
    }

    @ParameterizedTest
    @MethodSource("validSamples")
    public void shouldHaveRightNbParameters(@NonNull String definition, @NonNull @AggregateWith(ParsingAggregator.class) Parsing expected) {
        final var actual = parser.parse(definition);
        Assertions.assertEquals(expected.getNbParameters(),actual.getParameters().size());
    }

}
