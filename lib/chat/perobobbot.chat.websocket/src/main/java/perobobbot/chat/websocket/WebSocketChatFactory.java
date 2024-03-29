package perobobbot.chat.websocket;

import lombok.NonNull;
import perobobbot.chat.core.Chat;
import perobobbot.chat.core.ChatFactory;
import perobobbot.chat.core.ReconnectionPolicy;
import perobobbot.lang.Instants;

import java.net.URI;

public class WebSocketChatFactory extends ChatFactory {

    @Override
    public @NonNull Chat create(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy, @NonNull Instants instants) {
        if (this.canHandle(address,reconnectionPolicy)) {
            return new WebSocketChat(address, instants);
        }
        throw new IllegalArgumentException("Cannot create chat with provided parameters : address="+address);
    }

    @Override
    public boolean canHandle(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        final String scheme = address.getScheme();
        return scheme.equalsIgnoreCase("ws") || scheme.equalsIgnoreCase("wss");
    }
}
