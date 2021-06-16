package perobobbot.twitch.eventsub.manager.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebHookManager;
import perobobbot.lang.Packages;
import perobobbot.lang.chain.ChainExecutor;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.api.*;
import perobobbot.twitch.eventsub.manager.ToTwitchEventSubRequest;
import perobobbot.twitch.eventsub.manager._private.EventSubTwitchRequestTransformer;

@Configuration
public class EventSubConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("EventSub Configuration", EventSubConfiguration.class);
    }

    private final @NonNull String secret;
    private final @NonNull TwitchService twitchService;
    private final @NonNull ObjectMapper objectMapper;

    private final @NonNull EventSubWebhook eventSubWebhook;

    public EventSubConfiguration(
            @Value("${perobobbot.eventsub.path:/eventsub}") String eventSubPath,
            @Value("${perobobbot.eventsub.secret}") String secret,
            @NonNull WebHookManager webHookManager,
            @NonNull TwitchService twitchService,
            @NonNull ObjectMapper objectMapper) {
        this.secret = secret;
        this.twitchService = twitchService;
        this.objectMapper = objectMapper;
        this.eventSubWebhook = new EventSubWebhook(eventSubPath, webHookManager);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EventSubWebhook eventSubWebhook() {
        this.eventSubWebhook.setWebHookListener(twitchEventSubRequest());
        return eventSubWebhook;
    }

    @Bean
    public @NonNull TwitchEventSubRequest twitchEventSubRequest() {
        return new TwitchEventSubRequest(secret,
                                         new EventSubTwitchRequestTransformer(objectMapper),
                                         eventSubHandler());
    }

    @Bean
    public @NonNull EventSubHandler eventSubHandler() {
        final var bot = new ToTwitchEventSubRequest(eventSubWebhook::getCallbackURI, secret, twitchService);

        return new EventSubHandlerChainer(
                getChainForRequestFromTwitch(),
                getChainForSubscriptionDeletion(bot),
                getChainForSubscriptionCreation(bot)
        );
    }

    private ChainExecutor<TwitchRequestContent<EventSubRequest>> getChainForRequestFromTwitch() {
        return new ChainExecutor<>(ImmutableList.of(
                new DuplicateNotificationRemover(),
                new TwitchRequestContentEnricher(),
                new TwitchRequestContentDispatcher()
        ));
    }

    private ChainExecutor<SubscriptionWithLogin> getChainForSubscriptionDeletion(@NonNull ToTwitchEventSubRequest toTwitchEventSubRequest) {
        return new ChainExecutor<>(ImmutableList.of(
                new SubscriptionTerminator(toTwitchEventSubRequest)
        ));
    }

    private ChainExecutor<SubscriptionWithLogin> getChainForSubscriptionCreation(@NonNull ToTwitchEventSubRequest toTwitchEventSubRequest) {
        return new ChainExecutor<>(ImmutableList.of(
                new SubscriptionCreator(toTwitchEventSubRequest)
        ));
    }


}
