package bot.launcher.program;


import bot.common.lang.Subscription;
import bot.launcher.program.ChatProgram;
import bot.launcher.program.Command;
import bot.launcher.program.CommandExtractor;
import bot.twitch.chat.PrivMsgFromTwitchListener;
import bot.twitch.chat.TwitchChatIO;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import com.google.common.collect.ImmutableList;
import lombok.*;

public class ChatProgramManager {

    @NonNull
    private final TwitchChatIO twitchChatIO;

    @NonNull
    @Singular
    private final ImmutableList<ChatProgram> programs;

    private Subscription subscription = Subscription.NONE;

    public ChatProgramManager(@NonNull TwitchChatIO twitchChatIO, @NonNull ChatProgram... programs) {
        this(twitchChatIO,ImmutableList.copyOf(programs));
    }

    public ChatProgramManager(@NonNull TwitchChatIO twitchChatIO, @NonNull ImmutableList<ChatProgram> programs) {
        this.twitchChatIO = twitchChatIO;
        this.programs = programs;
    }

    @Synchronized
    public void start() {
        subscription.unsubscribe();
        subscription = twitchChatIO.addPrivateMessageListener(new Listener());
    }

    @Synchronized
    public void stop() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
    }

    private class Listener implements PrivMsgFromTwitchListener {

        @Override
        public void onPrivateMessage(@NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
            CommandExtractor.extract(reception.message().message())
                            .ifPresent(command -> handleCommand(reception,command));
        }

        private void handleCommand(@NonNull ReceivedMessage<PrivMsgFromTwitch> reception,@NonNull Command command) {
            for (ChatProgram program : programs) {
                if (program.handleCommand(twitchChatIO,reception,command)) {
                    return;
                }
            }
        }
    }
}
