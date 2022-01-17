package perococco.command;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.command.ParameterDefinition;

/**
 * Contains the information that can be used to
 * parse a chat command.
 */
@Value
public class CommandDefinitionParsingResult {

    public CommandDefinitionParsingResult(@NonNull String regexp, @NonNull String firstCommand, @NonNull String fullCommand, @NonNull ImmutableSet<ParameterDefinition> parameters) {
        this.regexp = regexp;
        this.firstCommand = firstCommand;
        this.fullCommand = fullCommand;
        this.parameters = parameters;
        this.maxNumberOfParameters = parameters.size();
        this.minNumberOfParameters = (int) parameters.stream().filter(p -> !p.isOptional()).count();
    }

    @NonNull String regexp;

    @NonNull String firstCommand;

    @NonNull String fullCommand;

    int minNumberOfParameters;

    int maxNumberOfParameters;

    @NonNull ImmutableSet<ParameterDefinition> parameters;

    public boolean isInConflictWith(CommandDefinitionParsingResult r2) {
        if (!this.fullCommand.equals(r2.fullCommand)) {
            return false;
        }

        return this.minNumberOfParameters <= r2.maxNumberOfParameters && this.maxNumberOfParameters >= r2.minNumberOfParameters;
    }
}
