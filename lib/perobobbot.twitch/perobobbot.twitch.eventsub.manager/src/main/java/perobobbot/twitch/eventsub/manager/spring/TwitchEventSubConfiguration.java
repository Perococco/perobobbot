package perobobbot.twitch.eventsub.manager.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.SubscriptionService;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.http.WebHookManager;
import perobobbot.lang.MessageGateway;
import perobobbot.lang.Nil;
import perobobbot.lang.Packages;
import perobobbot.lang.chain.ChainExecutor;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceWithToken;
import perobobbot.twitch.eventsub.api.*;
import perobobbot.twitch.eventsub.manager.EventSubRequestToTwitch;
import perobobbot.twitch.eventsub.manager.TwitchEventSubManager;
import perobobbot.twitch.eventsub.manager._private.EventSubTwitchRequestTransformer;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Configuration
public class TwitchEventSubConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("EventSub Configuration", TwitchEventSubConfiguration.class);
    }

    private final @NonNull String secret;
    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull MessageGateway messageGateway;
    private final @NonNull SubscriptionService subscriptionService;
    private final @NonNull
    @Qualifier("cachedThreadPool")
    ExecutorService executorService;

    private final @NonNull EventSubWebhook eventSubWebhook;
    private final @NonNull EventSubRequestToTwitch requestToTwitch;
    private final @NonNull TwitchService twitchService;

    public TwitchEventSubConfiguration(
            @Value("${perobobbot.eventsub.path:/eventsub}") String eventSubPath,
            @Value("${perobobbot.eventsub.secret}") String secret,
            @NonNull @Qualifier("cachedThreadPool") ExecutorService executorService,
            @NonNull @EventService SubscriptionService subscriptionService,
            @NonNull WebHookManager webHookManager,
            @NonNull TwitchService twitchService,
            @NonNull ObjectMapper objectMapper,
            @NonNull MessageGateway messageGateway) {
        this.secret = secret;
        this.objectMapper = objectMapper;
        this.messageGateway = messageGateway;
        this.executorService = executorService;
        this.twitchService = twitchService;
        this.subscriptionService = subscriptionService;
        this.eventSubWebhook = new EventSubWebhook(eventSubPath, webHookManager);
        this.requestToTwitch = new EventSubRequestToTwitch(eventSubWebhook::getCallbackURI, secret, twitchService);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EventSubWebhook eventSubWebhook() {
        this.eventSubWebhook.setWebHookListener(twitchEventSubListener());
        return eventSubWebhook;
    }

    @Bean
    public @NonNull TwitchEventSubListener twitchEventSubListener() {
        return new TwitchEventSubListener(executorService,
                                          secret,
                                          new EventSubTwitchRequestTransformer(objectMapper),
                                          eventSubHandler());
    }

    @Bean
    public @NonNull PlatformEventSubManager twitchEventSubManager() {
        return new TwitchEventSubManager(requestToTwitch, eventSubHandler(),twitchService);
    }

    @Bean
    public EventSubSubscriber eventSubSubscriber() {
        return requestToTwitch;
    }

    @Bean
    public @NonNull EventSubHandler eventSubHandler() {


        return new EventSubHandlerChainer(
                getChainForRequestFromTwitch(),
                getChainForSubscriptionDeletion(),
                getChainForSubscriptionCreation()
        );
    }

    private ChainExecutor<TwitchRequestContent<EventSubRequest>, Nil> getChainForRequestFromTwitch() {
        return new ChainExecutor<>(ImmutableList.of(
                new DuplicateNotificationRemover(),
                new TwitchRequestContentDispatcher(messageGateway)
        ));
    }

    private ChainExecutor<ObjectWithLogin<UUID>, Mono<Nil>> getChainForSubscriptionDeletion() {
        return new ChainExecutor<>(ImmutableList.of(
                new SubscriptionTerminator(requestToTwitch, subscriptionService)
        ));
    }

    private ChainExecutor<ObjectWithLogin<perobobbot.twitch.eventsub.api.subscription.Subscription>, Mono<UserSubscriptionView>> getChainForSubscriptionCreation() {
        return new ChainExecutor<>(ImmutableList.of(
                new SubscriptionCreator(requestToTwitch, subscriptionService)
        ));
    }


}
