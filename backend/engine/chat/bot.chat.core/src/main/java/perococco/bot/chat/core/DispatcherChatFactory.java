package perococco.bot.chat.core;

import bot.chat.core.Chat;
import bot.chat.core.ChatFactory;
import bot.chat.core.ReconnectionPolicy;
import bot.common.lang.ServiceLoaderHelper;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.net.URI;
import java.util.ServiceLoader;


public class DispatcherChatFactory extends ChatFactory {

    @NonNull
    public static ChatFactory getInstance() {
        return Holder.INSTANCE;
    }

    @NonNull
    private final ImmutableList<ChatFactory> factories;

    public DispatcherChatFactory() {
        this.factories = ServiceLoaderHelper.getServices(ServiceLoader.load(ChatFactory.class));
        if (factories.isEmpty()) {
            System.out.println("NO CHAT FACTORY FOUND");
        }
    }

    @Override
    public @NonNull Chat create(@NonNull URI address, @NonNull ReconnectionPolicy reconnectionPolicy) {
        return factories.stream()
                        .peek(f -> System.out.println("Chat Factory : "+f))
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
        private static final ChatFactory INSTANCE = new DispatcherChatFactory();
    }

}
