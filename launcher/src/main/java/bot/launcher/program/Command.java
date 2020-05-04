package bot.launcher.program;

import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Command {

    @NonNull
    private final String name;

    @NonNull
    private final ImmutableList<String> parameters;

    public boolean isCommandName(String commandName) {
        return name.equals(commandName);
    }
}
