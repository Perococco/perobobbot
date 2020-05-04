package bot.twitch.program;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public abstract class SimpleChatProgram extends ChatProgramBase{

    @NonNull
    private final ImmutableSet<String> myCommands;

    public SimpleChatProgram(@NonNull String myCommand) {
        this.myCommands = ImmutableSet.of(myCommand);
    }

    @Override
    protected boolean isOneOfMyCommand(@NonNull ProgramCommand command) {
        return myCommands.contains(command.name());
    }
}
