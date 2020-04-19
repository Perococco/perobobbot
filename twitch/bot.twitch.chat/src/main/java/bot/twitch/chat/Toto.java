package bot.twitch.chat;

import bot.chat.core.ChatManager;
import bot.chat.core.ChatManagerFactory;
import bot.chat.core.ReconnectionPolicy;
import bot.twitch.chat.message.to.Pass;
import lombok.NonNull;

import java.net.URI;

public class Toto {

    public static final URI TWITCH_SSL_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");

    public static ChatManager get(@NonNull URI address, @NonNull ReconnectionPolicy policy) {
        return ChatManagerFactory.getInstance().create(address, policy);
    }

    public static void main(String[] args) {
     final ChatManager chatManager =  get(TWITCH_SSL_URI, ReconnectionPolicy.NO_RECONNECTION);
        System.out.println(chatManager);
    }
}
