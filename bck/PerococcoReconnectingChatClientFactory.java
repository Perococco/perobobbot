package perococco.bot.chat.core;

import bot.chat.core.ReconnectionPolicy;
import bot.common.lang.Priority;
import lombok.NonNull;

/**
 * @author perococco
 **/
@Priority(Integer.MIN_VALUE)
public class PerococcoReconnectingChatClientFactory extends ReconnectingChatClientFactory {

    @Override
    public @NonNull ChatClient createReconnectingChatClient(@NonNull ChatClient chatClient,
            @NonNull ReconnectionPolicy policy) {
        return new PerococcoReconnectingChatClient(ReconnectionManager.factory(chatClient,policy));
    }

}
