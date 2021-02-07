package perococco.command;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.command.CommandParser;
import perobobbot.command.CommandParsing;
import perobobbot.command.ParameterDefinition;
import perobobbot.lang.MapTool;
import perobobbot.lang.fp.Value2;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

public class PeroCommandParser implements CommandParser {

    @Getter
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

        final String fullParameters;
        if (matcher.groupCount()>=1) {
            fullParameters = matcher.group(1).trim();
        } else {
            fullParameters = "";
        }


        final var parameters = parsingResult.getParameters()
                                            .stream()
                                            .map(ParameterDefinition::getName)
                                            .map(n -> Optional.ofNullable(matcher.group(n)).map(v -> Value2.of(n, v)))
                                            .flatMap(Optional::stream)
                                            .collect(MapTool.value2Collector());
        return Optional.of(new PeroCommandParsing(parsingResult.getFullCommand(), fullParameters, parameters));
    }

    @Override
    public boolean isInConflictWith(@NonNull CommandParser other) {
        if (other == this) {
            return false;
        }
        if (!other.getFullCommandName().equals(this.getFullCommandName())) {
            return false;
        }
        if (!(other instanceof PeroCommandParser)) {
            return false;
        }
        final var r1 = this.parsingResult;
        final var r2 = ((PeroCommandParser) other).parsingResult;

        return r1.isInConflictWith(r2);
    }
}
