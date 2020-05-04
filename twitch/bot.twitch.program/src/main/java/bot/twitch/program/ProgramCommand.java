package bot.twitch.program;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class ProgramCommand {

    @NonNull
    private final String name;

    @NonNull
    private final ImmutableList<String> parameters;

    public boolean isCommandName(String commandName) {
        return name.equals(commandName);
    }
}
