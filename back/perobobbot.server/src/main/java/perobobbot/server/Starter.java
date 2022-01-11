package perobobbot.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.IO;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.com.UserSubscriptionView;
import perobobbot.data.service.*;
import perobobbot.eventsub.UserEventSubManager;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.LoginTokenIdentifier;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.oauth.UserOAuthInfo;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.channelpoints.CreateCustomRewardParameter;
import perobobbot.twitch.client.api.channelpoints.CustomReward;
import perobobbot.twitch.eventsub.api.CriteriaType;
import perobobbot.twitch.eventsub.api.SubscriptionType;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.stream.Stream;

//@Component
@RequiredArgsConstructor
public class Starter {

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

    private final @NonNull IO io;


    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        try {

//            Thread.sleep(5000);
//
//            joinChannel();
//            final var authInfo = oAuthService.createUserToken("perococco", Platform.TWITCH);
//
//            Desktop.getDesktop().browse(authInfo.getOauthURI());
//            authInfo.get();


//            joinChannel();
//            OAuthContextHolder.getContext().setTokenIdentifier(new LoginTokenIdentifier("perococco"));
//            final var bot = botService.findBotByName("perococco","Perobobbot").get();
//
//            final var viewerId = viewerIdentityService.findIdentity(Platform.TWITCH, "perococco");
//            botService.addJoinedChannel(bot.getId(),viewerId.get().getId(),"pantoufl");

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
//            twitchService.createCustomReward(CreateCustomRewardParameter.builder().title("Convert 100").cost(100).build()).subscribe();
//            twitchService.createCustomReward(CreateCustomRewardParameter.builder().title("Convert 1000").cost(1000).build()).subscribe();
//            twitchService.createCustomReward(CreateCustomRewardParameter.builder().title("Convert 10000").cost(10000).build()).subscribe();
//            twitchService.getCustomReward(new GetCustomRewardsParameter(new String[0], true))
//                         .subscribe(s -> System.out.println(s.getId() + "'"+s.getTitle()+"'"));
//            createClient();
//            joinChannel();
        } catch (Throwable t) {
            System.out.println("Starter Failed ! ");
            t.printStackTrace();
        }
    }

    private void createSubscriptions() {
        final var identity = platformUserService.getPlatformUser(Platform.TWITCH, "perococco");
        final var conditions = Conditions.with(CriteriaType.BROADCASTER_USER_ID, identity.getUserId());
        final var login = "perococco";
        final var toSubscriptionData = Function1.toFunction1((SubscriptionType s) -> new SubscriptionData(Platform.TWITCH, s, conditions));
        OAuthContextHolder.getContext().setTokenIdentifier(new LoginTokenIdentifier(login));


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
            userEventSubManager.createUserSubscription(login, subscriptionDatum).subscribe();
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
                                      .filter(d -> d.getUserLogin().equals(chatUser))
                                      .findAny()
                                      .get();

        final var connectionInfo = ChatConnectionInfo.builder()
                                                     .botId(bot.getId())
                                                     .platformUserId(token.getPlatformUser().getId())
                                                     .platform(Platform.TWITCH)
                                                     .botName(bot.getName())
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
