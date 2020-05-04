package bot.launcher.program;

import bot.chat.advanced.DispatchContext;
import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatIO;
import bot.twitch.program.ProgramCommand;
import bot.twitch.program.SimpleChatProgram;
import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;

public class PingProgram extends SimpleChatProgram {

    public PingProgram() {
        super("myping");
    }

    @Override
    public @NonNull String name() {
        return "ping";
    }

    @Override
    public boolean doHandleCommand(
            @NonNull TwitchChatIO twitchChatIO,
            @NonNull ProgramCommand command
    ) {
        final Channel channel = command.channel();
        twitchChatIO.message(channel,d -> buildPongMessage(d, command.receptionTime()));
        return true;
    }

    private String buildPongMessage(@NonNull DispatchContext context, @NonNull Instant receptionTime) {
        final Duration duration = Duration.between(receptionTime,context.time());
        return "mypong "+duration.toNanos();
    }

}
