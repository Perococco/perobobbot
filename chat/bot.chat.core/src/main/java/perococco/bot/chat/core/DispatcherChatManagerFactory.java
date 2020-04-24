package perococco.bot.chat.core;

import bot.chat.core.Chat;
import bot.chat.core.ChatManagerFactory;
import bot.chat.core.ReconnectionPolicy;
import bot.common.lang.ServiceLoaderHelper;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.net.URI;
import java.util.ServiceLoader;


public class DispatcherChatManagerFactory extends ChatManagerFactory {

    @NonNull
    public static ChatManagerFactory getInstance() {
        return Holder.INSTANCE;
    }

    @NonNull
    private final ImmutableList<ChatManagerFactory> factories;

    public DispatcherChatManagerFactory() {
        this.factories = ServiceLoaderHelper.getServices(ServiceLoader.load(ChatManagerFactory.class));
    }

    @Override
    public @NonNull Chat create(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        return factories.stream()
                        .filter(f -> f.canHandle(address,reconnectionPolicy))
                        .findFirst()
                        .orElseThrow(() -> buildCannotHandleException(address,reconnectionPolicy))
                        .create(address,reconnectionPolicy);
    }

    @Override
    public boolean canHandle(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        return factories.stream().anyMatch(f -> f.canHandle(address,reconnectionPolicy));
    }

    private static class Holder {
        private static final ChatManagerFactory INSTANCE = new DispatcherChatManagerFactory();
    }

}
