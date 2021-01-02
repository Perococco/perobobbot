package perobobbot.command;

import lombok.NonNull;

public class UnknownParameter  extends CommandException {

    private final @NonNull String commandName;

    private final @NonNull String parameterName;

    public UnknownParameter(@NonNull String commandName, @NonNull String parameterName) {
        super("No parameter named '"+parameterName+"' for command '"+commandName+"'");
        this.commandName = commandName;
        this.parameterName = parameterName;
    }
}
