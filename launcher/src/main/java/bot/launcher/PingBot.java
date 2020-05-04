package bot.launcher;

import bot.common.lang.Subscription;
import bot.twitch.chat.Channel;
import bot.twitch.chat.PrivMsgFromTwitchListener;
import bot.twitch.chat.TwitchChatIO;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.NonNull;
import lombok.Synchronized;

public class PingBot implements PrivMsgFromTwitchListener {

    @NonNull
    private final TwitchChatIO chat;

    private Subscription subscription = Subscription.NONE;

    public PingBot(@NonNull TwitchChatIO chat) {
        this.chat = chat;
    }

    @Synchronized
    public void start() {
        subscription.unsubscribe();
        subscription = chat.addPrivateMessageListener(this);
    }

    @Synchronized
    public void stop() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
    }

    @Override
    public void onPrivateMessage(@NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
        final String message = reception.message().message();
        CommandExtractor.extract(message)
                        .filter(c -> c.equals("myping"))
                        .ifPresent(c -> sendPong(reception));
    }

    private void sendPong(@NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
        final Channel channel = reception.message().channel();
        chat.message(channel,"mypong");
    }

}
