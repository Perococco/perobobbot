package bot.chat.websocket;

import bot.chat.core.Chat;
import bot.chat.core.ChatManagerFactory;
import bot.chat.core.ReconnectionPolicy;
import lombok.NonNull;

import java.net.URI;

public class WebSocketChatManagerFactory extends ChatManagerFactory {

    @Override
    public @NonNull Chat create(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        if (this.canHandle(address,reconnectionPolicy)) {
            return new WebSocketChat(address, reconnectionPolicy);
        }
        throw new IllegalArgumentException("Cannot create chat with provided parameters : address="+address);
    }

    @Override
    public boolean canHandle(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        final String scheme = address.getScheme();
        return scheme.equalsIgnoreCase("ws") || scheme.equalsIgnoreCase("wss");
    }
}
