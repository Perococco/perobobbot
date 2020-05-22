package perococco.perobobbot.chat.core;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.chat.core.Chat;
import perobobbot.chat.core.ChatFactory;
import perobobbot.chat.core.ReconnectionPolicy;
import perobobbot.common.lang.ServiceLoaderHelper;

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
        private static final ChatFactory INSTANCE = new DispatcherChatFactory();
    }

}
