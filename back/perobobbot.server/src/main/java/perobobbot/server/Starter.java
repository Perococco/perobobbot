package perobobbot.server;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.IO;
import perobobbot.data.service.*;
import perobobbot.eventsub.UserEventSubManager;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;
import perobobbot.oauth.LoginTokenIdentifier;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.server.component.ClientUpdater;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.channelpoints.CreateCustomRewardParameter;
import perobobbot.twitch.client.api.channelpoints.CustomReward;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

@SuppressWarnings("all")
@Component
@RequiredArgsConstructor
public class Starter {


    public static final String LOGIN = "perococco";
    public static final String BOT_NAME = "Perobobbot";

    private final @NonNull
    @UnsecuredService
    ClientService clientService;

    private final @NonNull
    @UnsecuredService
    PlatformUserService platformUserService;

    private final @NonNull
    UserEventSubManager userEventSubManager;

    private final @NonNull
    @UnsecuredService
    BotService botService;

    private final @NonNull
    @UnsecuredService
    OAuthService oAuthService;

    private final @NonNull
    TwitchService twitchService;

    private final @NonNull ClientUpdater clientUpdater;

    private final @NonNull IO io;


    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        try {

//            Optional<PlatformUser> perococco = platformUserService.findPlatformUser(Platform.TWITCH, "perococco");
//            ImmutableList<PlatformBot> perococco1 = botService.listPlatformBotsForBotName("perococco", BOT_NAME);
//            System.out.println(perococco1);
//            Thread.sleep(5000);
//            this.setupClients();
//            this.setupTokens();
            final var bot = this.setupBots();
            final var platformBot = this.setupPlatformBot(bot);

//            createSubscriptions();
//            joinChannel(platformBot);

        } catch (Throwable t) {
            System.out.println("Starter Failed ! ");
            t.printStackTrace();
        }
    }

    private void setupClients() throws IOException {
        if (clientService.findAllClients().isEmpty()) {
            clientUpdater.update();
        }
    }

    private void setupTokens() throws ExecutionException, InterruptedException, IOException {
        System.out.println("Twitch Token for perococco : user perococco");
        setupToken(Platform.TWITCH);
        System.out.println("Twitch Token for perococco : user perobobbot");
        setupToken(Platform.TWITCH);
        System.out.println("Discord Token for perococco");
        setupToken(Platform.DISCORD);
    }

    private void setupToken(@NonNull Platform platform) throws IOException, ExecutionException, InterruptedException {
        final var userToken = oAuthService.createUserToken(LOGIN, platform);
        Desktop.getDesktop().browse(userToken.getOauthURI());
        userToken.get();
    }


    private Bot setupBots() {
        final var bot = botService.findBotByName(LOGIN, BOT_NAME);
        if (bot.isPresent()) {
            return bot.get();
        }
        return botService.createBot(LOGIN, BOT_NAME);
    }

    private @NonNull PlatformBot setupPlatformBot(@NonNull Bot bot) {
        {
            final var platformBot = botService
                    .listPlatformBotsForBotName(LOGIN, BOT_NAME)
                    .stream()
                    .filter(b -> b.getPlatform() == Platform.TWITCH)
                    .findFirst()
                    .orElse(null);
            if (platformBot != null) {
                return platformBot;
            }
        }

        final var platformUser = platformUserService.getPlatformUser(Platform.TWITCH, "perobobbot");

        final var platformBot = botService.createPlatformBot(bot.getId(), platformUser.getId());
        return platformBot;
    }


    private void createSubscriptions() {
        final var identity = platformUserService.getPlatformUser(Platform.TWITCH, LOGIN);
        final var conditions = Conditions.with(CriteriaType.BROADCASTER_USER_ID, identity.getUserId());
        final var toSubscriptionData = Function1.toFunction1((SubscriptionType s) -> new SubscriptionData(Platform.TWITCH, s, conditions));
        OAuthContextHolder.getContext().setTokenIdentifier(new LoginTokenIdentifier(LOGIN));


        final var subscriptionData = Stream.of(
                SubscriptionType.CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_ADD,
                SubscriptionType.CHANNEL_UPDATE,
                SubscriptionType.CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REDEMPTION_UPDATE,
                SubscriptionType.CHANNEL_FOLLOW,
                SubscriptionType.CHANNEL_SUBSCRIBE,
                SubscriptionType.CHANNEL_SUBSCRIPTION_GIFT,
                SubscriptionType.CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_ADD,
                SubscriptionType.CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_REMOVE,
                SubscriptionType.CHANNEL_CHANNEL_POINTS_CUSTOM_REWARD_UPDATE
        ).map(toSubscriptionData).toList();

        for (SubscriptionData subscriptionDatum : subscriptionData) {
            userEventSubManager.createUserSubscription(LOGIN, subscriptionDatum).subscribe();
        }

    }

    private @NonNull Mono<CustomReward> create(@NonNull String name, int cost) {
        return twitchService.createCustomReward(CreateCustomRewardParameter.builder()
                                                                           .title(name)
                                                                           .cost(cost)
                                                                           .build());
    }


    private void joinChannel(@NonNull PlatformBot platformBot) {
        final var userLogin = "perococco";
        final var chatUser = "perobobbot";


        final var token = oAuthService.findUserToken(LOGIN, Platform.TWITCH)
                                      .stream()
                                      .filter(d -> d.getUserLogin().equals(chatUser))
                                      .findAny()
                                      .get();

        final var connectionInfo = ChatConnectionInfo.builder()
                                                     .platformBot(platformBot)
                                                     .nick(token.getUserLogin())
                                                     .secret(token.getAccessToken())
                                                     .build();

        io.getPlatform(Platform.TWITCH)
          .connect(connectionInfo)
          .thenCompose(c -> c.join("perococco"))
          .whenComplete((r, e) -> {
              if (e != null) {
                  System.err.println("Fail to join channel of perococco");
                  e.printStackTrace();
              } else {
                  r.send("Hello here !");
              }
          });
    }

}
