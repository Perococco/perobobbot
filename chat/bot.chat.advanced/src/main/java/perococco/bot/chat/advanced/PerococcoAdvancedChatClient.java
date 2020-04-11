package perococco.bot.chat.advanced;

import bot.chat.advanced.*;
import bot.chat.core.Chat;
import bot.chat.core.ChatClient;
import bot.chat.core.ChatClientListener;
import bot.common.lang.Listeners;
import bot.common.lang.Subscription;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public class PerococcoAdvancedChatClient implements AdvancedChatClient, ChatClientListener {

    @NonNull
    public static AdvancedChatClientFactory provider() {
        return new AdvancedChatClientFactory() {
            @Override
            public @NonNull AdvancedChatClient createBasedOn(
                    @NonNull ChatClient chatClient,
                    @NonNull RequestAnswerMatcher matcher,
                    @NonNull MessageConverter messageConverter) {
                return new PerococcoAdvancedChatClient(chatClient, matcher, messageConverter);
            }

            @Override
            public int priority() {
                return Integer.MIN_VALUE;
            }
        };
    }


    @NonNull
    private final ChatClient chatClient;

    @NonNull
    private final RequestAnswerMatcher matcher;

    @NonNull
    private final MessageConverter messageConverter;

    private final Listeners<AdvancedChatClientListener> listeners = new Listeners<>();

    private PerococcoAdvancedChat advancedChat = null;

    private Subscription subscription = Subscription.NONE;

    @Override
    @Synchronized
    public @NonNull AdvancedChat connect() {
        if (advancedChat != null) {
            return advancedChat;
        }
        subscription.unsubscribe();
        subscription = chatClient.addChatClientListener(this);
        try {
            chatClient.connect();
        } catch (Throwable e) {
            subscription.unsubscribe();
            subscription = Subscription.NONE;
            throw e;
        }
        return advancedChat;
    }

    @Override
    @Synchronized
    public void disconnect() {
        chatClient.disconnect();
        subscription.unsubscribe();
        subscription = Subscription.NONE;
    }

    @Override
    public @NonNull Subscription addChatClientListener(@NonNull AdvancedChatClientListener listener) {
        return listeners.addListener(listener);
    }

    @Override
    public void onConnection(@NonNull Chat chat) {
        this.advancedChat = new PerococcoAdvancedChat(chat, matcher, messageConverter);
        this.advancedChat.start();
        listeners.warnListeners(AdvancedChatClientListener::onConnection, advancedChat);
    }

    @Override
    public void onDisconnection() {
        listeners.warnListeners(AdvancedChatClientListener::onDisconnection);
    }

}
