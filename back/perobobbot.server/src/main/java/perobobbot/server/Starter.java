package perobobbot.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.IO;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.BotService;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.eventsub.UserEventSubManager;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.oauth.LoginTokenIdentifier;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.channelpoints.CreateCustomRewardParameter;
import perobobbot.twitch.client.api.channelpoints.CustomReward;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class Starter {

    private final @NonNull
    @UnsecuredService
    ClientService clientService;

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

    private final @NonNull IO io;


    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
        try {
            OAuthContextHolder.getContext().setTokenIdentifier(new LoginTokenIdentifier("perococco"));
//            userEventSubManager.createUserSubscription("perococco",new SubscriptionData(
//                    Platform.TWITCH,
//                    SubscriptionType.CHANNEL_SUBSCRIBE,
//                    Conditions.with(CriteriaType.BROADCASTER_USER_ID,"211307900")
//            )).subscribe(s -> System.out.println(s));
//            userEventSubManager.createUserSubscription("perococco",new SubscriptionData(
//                    Platform.TWITCH,
//                    SubscriptionType.CHANNEL_SUBSCRIPTION_GIFT,
//                    Conditions.with(CriteriaType.BROADCASTER_USER_ID,"211307900")
//            )).subscribe(s -> System.out.println(s));
//            twitchService.getChannelInformation("211307900").subscribe(s -> System.out.println(s));
//            twitchService.createCustomReward(CreateCustomRewardParameter.builder().title("Créer une Horde").cost(100).build()).subscribe();
//            twitchService.getCustomReward(new GetCustomRewardsParameter(new String[0], true))
//                         .subscribe(s -> System.out.println(s.getId() + "'"+s.getPrompt()+"'"));
//            createClient();
//            joinChannel();
        } catch (Throwable t) {
            System.out.println("Starter Failed ! ");
            t.printStackTrace();
        }
    }

    private @NonNull Mono<CustomReward> create(@NonNull String name, int cost) {
        return twitchService.createCustomReward(CreateCustomRewardParameter.builder()
                                                                           .title(name)
                                                                           .cost(cost)
                                                                           .build());
    }


    private void joinChannel() {
        final var userLogin = "perococco";
        final var chatUser = "perobobbot";
        final var bot = botService.findBotByName(userLogin, "Perobobbot").get();

        final var token = oAuthService.findUserToken(userLogin, Platform.TWITCH)
                                      .stream()
                                      .filter(d -> d.getViewerLogin().equals(chatUser))
                                      .findAny()
                                      .get();

        final var connectionInfo = ChatConnectionInfo.builder()
                                                     .botId(bot.getId())
                                                     .viewerIdentityId(token.getViewerIdentity().getId())
                                                     .platform(Platform.TWITCH)
                                                     .botName(bot.getName())
                                                     .nick(token.getViewerLogin())
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

    private void createClient() throws IOException {
        final var existing = clientService.findClientForPlatform(Platform.TWITCH).orElse(null);
        if (existing == null) {
            final var clientId = "m01e1fb0emhtr0toc6eydvl9zkuecu";
            final var clientSecret = Secret.with(Files.readAllLines(Path.of("/home/perococco/twitch_keys/perobobbot_app_secret.txt")).get(0));
            clientService.createClient(new CreateClientParameter(
                    Platform.TWITCH,
                    clientId,
                    clientSecret
            ));
        }
    }
}
