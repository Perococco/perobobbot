package bot.twitch.program;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;

public abstract class SimpleChatProgram extends ChatProgramBase {

    @NonNull
    @Getter
    private final ImmutableSet<String> commands;

    public SimpleChatProgram(@NonNull String myCommand) {
        this.commands = ImmutableSet.of(myCommand);
    }

    @Override
    protected boolean isOneOfMyCommand(@NonNull ProgramCommand command) {
        return commands.contains(command.commandName());
    }
}
