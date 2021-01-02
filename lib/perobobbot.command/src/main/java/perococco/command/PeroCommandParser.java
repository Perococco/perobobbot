package perococco.command;

import lombok.NonNull;
import perobobbot.command.CommandParser;
import perobobbot.command.CommandParsing;
import perobobbot.lang.MapTool;
import perobobbot.lang.fp.Value2;

import java.util.Optional;
import java.util.regex.Pattern;

public class PeroCommandParser implements CommandParser {

    private final @NonNull String commandDefinition;
    private final @NonNull CommandRegexpParser.Result parsingResult;
    private final @NonNull Pattern pattern;

    public PeroCommandParser(@NonNull String commandDefinition) {
        this.commandDefinition = commandDefinition.trim();
        this.parsingResult = CommandRegexpParser.parse(this.commandDefinition);
        this.pattern = Pattern.compile(this.parsingResult.getRegexp());
    }

    @Override
    public @NonNull String getFullCommandName() {
        return parsingResult.getFullCommand();
    }

    @Override
    public @NonNull Optional<CommandParsing> parse(@NonNull String command) {
        if (!command.startsWith(parsingResult.getFirstCommand())) {
            return Optional.empty();
        }

        final var matcher = this.pattern.matcher(command);
        if (!matcher.matches()) {
            return Optional.empty();
        }
        final var parameters = parsingResult.getParameters()
                                            .stream()
                                            .map(p -> p.getName())
                                            .map(n -> Optional.ofNullable(matcher.group(n)).map(v -> Value2.of(n, v)))
                                            .flatMap(Optional::stream)
                                            .collect(MapTool.value2Collector());
        return Optional.of(new PeroCommandParsing(parsingResult.getFullCommand(), parameters));
    }
}
