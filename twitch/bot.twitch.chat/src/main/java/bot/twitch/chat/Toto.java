package bot.twitch.chat;

import bot.chat.core.Chat;
import bot.chat.core.ChatManagerFactory;
import bot.chat.core.ReconnectionPolicy;
import lombok.NonNull;

import java.net.URI;

public class Toto {

    public static final URI TWITCH_SSL_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");

    public static Chat get(@NonNull URI address, @NonNull ReconnectionPolicy policy) {
        return ChatManagerFactory.getInstance().create(address, policy);
    }

    public static void main(String[] args) {
     final Chat chat =  get(TWITCH_SSL_URI, ReconnectionPolicy.NO_RECONNECTION);
        System.out.println(chat);
    }
}
