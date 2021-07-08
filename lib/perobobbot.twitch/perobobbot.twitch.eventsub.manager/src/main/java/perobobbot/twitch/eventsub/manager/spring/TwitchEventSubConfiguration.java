package perobobbot.twitch.eventsub.manager.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.eventsub.PlatformEventSubManager;
import perobobbot.http.WebHookManager;
import perobobbot.lang.MessageGateway;
import perobobbot.lang.Packages;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.eventsub.manager.TwitchEventSubManager;
import perobobbot.twitch.eventsub.manager.TwitchEventSubSubscriber;
import perobobbot.twitch.eventsub.manager._private.EventSubTwitchRequestTransformer;
import perobobbot.twitch.eventsub.manager.spring.handler.DuplicatedNotificationHandler;
import perobobbot.twitch.eventsub.manager.spring.handler.TwitchNotificationDispatcher;

import java.util.concurrent.ExecutorService;

@Configuration
public class TwitchEventSubConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("EventSub Configuration", TwitchEventSubConfiguration.class);
    }

    private final @NonNull String secret;
    private final @NonNull ObjectMapper objectMapper;
    private final @NonNull MessageGateway messageGateway;
    private final @NonNull
    @Qualifier("cachedThreadPool")
    ExecutorService executorService;

    private final @NonNull EventSubWebhook eventSubWebhook;
    private final @NonNull TwitchEventSubSubscriber requestToTwitch;
    private final @NonNull TwitchService twitchService;

    public TwitchEventSubConfiguration(
            @Value("${perobobbot.eventsub.path:/eventsub}") String eventSubPath,
            @Value("${perobobbot.eventsub.secret}") String secret,
            @NonNull @Qualifier("cachedThreadPool") ExecutorService executorService,
            @NonNull WebHookManager webHookManager,
            @NonNull TwitchService twitchService,
            @NonNull ObjectMapper objectMapper,
            @NonNull MessageGateway messageGateway) {
        this.secret = secret;
        this.objectMapper = objectMapper;
        this.messageGateway = messageGateway;
        this.executorService = executorService;
        this.twitchService = twitchService;
        this.eventSubWebhook = new EventSubWebhook(eventSubPath, webHookManager);
        this.requestToTwitch = new TwitchEventSubSubscriber(eventSubWebhook::getCallbackURI, secret, twitchService);
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EventSubWebhook eventSubWebhook() {
        this.eventSubWebhook.setWebHookListener(twitchEventSubListener());
        return eventSubWebhook;
    }

    @Bean
    public @NonNull TwitchEventSubListener twitchEventSubListener() {
        final var handler =
                new DuplicatedNotificationHandler()
                .then(new TwitchNotificationDispatcher(messageGateway));

        return new TwitchEventSubListener(executorService,
                secret,
                new EventSubTwitchRequestTransformer(objectMapper),
                handler);
    }

    @Bean
    public @NonNull PlatformEventSubManager twitchEventSubManager() {
        return new TwitchEventSubManager(requestToTwitch, twitchService);
    }

}
