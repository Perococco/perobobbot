package perococco.command;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.command.CommandDefinitionParsingFailure;
import perobobbot.command.ParameterDefinition;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandRegexpParser {

    public static final String ARGUMENT_SINGLE_QUOTE = "'(?:[^ '\\\\]|\\\\.)+'";
    public static final String ARGUMENT_DOUBLE_QUOTE = "\"(?:[^ \"\\\\]|\\\\.)+\"";
    public static final String ARGUMENT_NO_QUOTE = "[^ \"']+";

    public static final String ARGUMENT_PATTERN = "(" + ARGUMENT_SINGLE_QUOTE + "|" + ARGUMENT_DOUBLE_QUOTE + "|" + ARGUMENT_NO_QUOTE + ")";

    public static @NonNull CommandRegexpParser.Result parse(@NonNull String commandDefinition) {
        return new CommandRegexpParser(commandDefinition.trim()).parse();
    }

    private final @NonNull String commandDefinition;

    private final StringBuilder regexpBuilder = new StringBuilder();

    private final Set<ParameterDefinition> parameterDefinitions = new HashSet<>();

    private final StringBuilder fullCommandBuilder = new StringBuilder();

    private int index = 0;

    private boolean inFullCommand = true;

    private boolean spacePending = false;

    private @NonNull CommandRegexpParser.Result parse() {
        this.startPattern();
        while (index < commandDefinition.length()) {
            parseToken();
        }
        this.closePattern();
        final var fullCommand = this.getFullCommand();
        final var firstCommand = extractFirstCommand(fullCommand);
        return new Result(regexpBuilder.toString(),
                                              firstCommand,
                                              fullCommand,
                                              ImmutableSet.copyOf(parameterDefinitions));
    }

    private @NonNull String extractFirstCommand(@NonNull String fullCommand) {
        final var idx = fullCommand.indexOf("|");
        if (idx<0) {
            return fullCommand;
        }
        return fullCommand.substring(0,idx);
    }

    private String getFullCommand() {
        final var fullCommand = this.fullCommandBuilder.toString();
        if (!fullCommand.chars().allMatch(c -> c == '|' || isValidChar(c))) {
            throw createError("Command must start with an alpha-numeric word");
        }
        return fullCommand;
    }

    private void startPattern() {
        this.regexpBuilder.append("^");
    }

    private void closePattern() {
        this.regexpBuilder.append("$");
    }

    private void parseToken() {
        final char c = commandDefinition.charAt(index++);
        switch (c) {
            case '[','{' -> {
                inFullCommand = false;
                parseParameter(c=='[');
            }
            case ' ' -> spacePending = true;
            default -> {
                if (spacePending) {
                    if (inFullCommand) {
                        fullCommandBuilder.append("|");
                    }
                    regexpBuilder.append("\s+");
                }
                if (inFullCommand) {
                    fullCommandBuilder.append(c);
                }
                appendChar(c);
                spacePending = false;
            }
        }
    }

    private void appendChar(char c) {
        regexpBuilder.append(escapeChar(c));
    }

    private String escapeChar(char c) {
        return switch (c) {
            case '.', '*', '?', '-' -> "\\"+c;
            default -> ""+c;
        };
    }


    private void parseParameter(boolean optional) {
        final var parameterName = extractParameterName(optional ? ']' : '}');
        this.parameterDefinitions.add(new ParameterDefinition(parameterName, optional));
        regexpBuilder.append(optional ? optionalParameter(parameterName) : requiredParameter(parameterName));
        spacePending = false;
    }

    private @NonNull String extractParameterName(char closingChar) {
        final int idx = commandDefinition.indexOf(closingChar, index);
        if (idx < 0) {
            throw createError("No closing char for parameter");
        }
        final var name = commandDefinition.substring(index, idx);
        if (!isValidParameterName(name)) {
            throw createError("Invalid parameter name '"+name+"'");
        }
        index = idx + 1;
        return name;
    }

    private boolean isValidParameterName(@NonNull String name) {
        return (!name.isEmpty() && name.chars().allMatch(this::isValidChar));
    }

    private boolean isValidChar(int c) {
        return c == '-' || c == '.' || c == '?' || (c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    private @NonNull String requiredParameter(@NonNull String name) {
        final var base = "(?<" + name + ">" + CommandRegexpParser.ARGUMENT_PATTERN + ")";
        if (spacePending) {
            spacePending = false;
            return "\s+"+base;
        } else {
            return base;
        }
    }

    private @NonNull String optionalParameter(@NonNull String name) {
        final var base = "(?<" + name + ">" + CommandRegexpParser.ARGUMENT_PATTERN + ")";
        if (spacePending) {
            spacePending = false;
            return "(\s+"+base+")?";
        } else {
            return base+"?";
        }
    }


    private @NonNull CommandDefinitionParsingFailure createError(@NonNull String reason) {
        return new CommandDefinitionParsingFailure(reason, commandDefinition);
    }


    @Value
    public static class Result {

        public Result(@NonNull String regexp, @NonNull String firstCommand, @NonNull String fullCommand, @NonNull ImmutableSet<ParameterDefinition> parameters) {
            this.regexp = regexp;
            this.firstCommand = firstCommand;
            this.fullCommand = fullCommand;
            this.parameters = parameters;
            this.maxNumberOfParameters = parameters.size();
            this.minNumberOfParameters = (int)parameters.stream().filter(p -> !p.isOptional()).count();
        }

        @NonNull String regexp;

        @NonNull String firstCommand;

        @NonNull String fullCommand;

        int minNumberOfParameters;

        int maxNumberOfParameters;

        @NonNull ImmutableSet<ParameterDefinition> parameters;

        public boolean isInConflictWith(Result r2) {
            if (!this.fullCommand.equals(r2.fullCommand)) {
                return false;
            }

            return this.minNumberOfParameters <= r2.maxNumberOfParameters && this.maxNumberOfParameters >= r2.minNumberOfParameters;
        }
    }
}
