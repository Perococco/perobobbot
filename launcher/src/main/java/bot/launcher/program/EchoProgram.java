package bot.launcher.program;

import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatIO;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import bot.twitch.program.ProgramCommand;
import bot.twitch.program.SimpleChatProgram;
import lombok.NonNull;

import java.util.stream.Collectors;

public class EchoProgram extends SimpleChatProgram {

    public EchoProgram() {
        super("echo");
    }

    @Override
    protected boolean doHandleCommand(@NonNull TwitchChatIO twitchChatIO, @NonNull ReceivedMessage<PrivMsgFromTwitch> reception, @NonNull ProgramCommand command) {
        final Channel channel = reception.message().channel();
        final String user = reception.message().user();
        final String message =command.parameters().stream().collect(Collectors.joining(" ",user+" said '", "'"));
        twitchChatIO.message(channel,message);
        return true;
    }

}
