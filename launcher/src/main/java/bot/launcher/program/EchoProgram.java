package bot.launcher.program;

import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatIO;
import bot.twitch.program.ProgramCommand;
import bot.twitch.program.SimpleChatProgram;
import lombok.NonNull;

import java.util.stream.Collectors;

public class EchoProgram extends SimpleChatProgram {

    public EchoProgram() {
        super("echo");
    }

    @Override
    public @NonNull String name() {
        return "echo";
    }

    @Override
    protected boolean doHandleCommand(@NonNull TwitchChatIO twitchChatIO, @NonNull ProgramCommand command) {
        final Channel channel = command.channel();
        final String user = command.user();
        final String message =command.parameters().stream().collect(Collectors.joining(" ",user+" said '", "'"));
        twitchChatIO.message(channel,message);
        return true;
    }

}
