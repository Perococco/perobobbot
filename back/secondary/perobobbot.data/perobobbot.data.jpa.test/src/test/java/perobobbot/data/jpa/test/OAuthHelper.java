package perobobbot.data.jpa.test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import perobobbot.lang.*;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.Token;
import perobobbot.oauth.UserOAuthInfo;
import uk.co.jemos.podam.api.PodamFactory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Value
public class OAuthHelper {

    @NonNull ImmutableMap<Platform, PlatformHelper> platformHelpers;


    public void mockOAuthManager(@NonNull OAuthManager oAuthManager) {
        platformHelpers.forEach((k, v) -> v.mockOAuthManager(oAuthManager));
    }

    public UserIdentity getIdentity(Platform platform, int index) {
        return platformHelpers.get(platform).getIdentity(index);
    }

    @Value
    @lombok.Builder
    public static class PlatformHelper {
        @NonNull DecryptedClient client;
        @Singular
        @NonNull ImmutableList<UserInfo> userInfos;

        public @NonNull Platform getPlatform() {
            return client.getPlatform();
        }

        public @NonNull UserIdentity getIdentity(int index) {
            return this.userInfos.get(index).getIdentity();
        }

        public void mockOAuthManager(@NonNull OAuthManager oAuthManager) {
            final var userOAuthInfo = ListTool.map(userInfos, u -> new UserOAuthInfo<>(URI.create("https://localhost"), CompletableFuture.completedFuture(u.getToken())));

            Mockito.when(oAuthManager.prepareUserOAuth(Mockito.eq(client), Mockito.any())).thenAnswer(new Answer<UserOAuthInfo<Token>>() {
                private int count = 0;
                @Override
                public UserOAuthInfo<Token> answer(InvocationOnMock invocationOnMock) throws Throwable {
                    final var result = userOAuthInfo.get(count);
                    count++;
                    return result;
                }
            });

            for (UserInfo userInfo : userInfos) {
                Mockito.when(oAuthManager.getUserIdentity(client.getPlatform(), userInfo.getToken().getAccessToken()))
                       .thenReturn(CompletableFuture.completedFuture(userInfo.getIdentity()));
            }
        }
    }

    @Value
    private static class UserInfo {
        @NonNull Token token;
        @NonNull UserIdentity identity;

        public UserInfo(@NonNull PodamFactory podamFactory, @NonNull Platform platform) {
            this.token = podamFactory.manufacturePojo(Token.class);
            final var type = switch (platform) {
                case DISCORD -> DiscordIdentity.class;
                case TWITCH -> TwitchIdentity.class;
                default -> Todo.<Class<UserIdentity>>TODO(platform.getIdentification());
            };
            this.identity = podamFactory.manufacturePojo(type);
        }
    }


    public static Builder builder(@NonNull PodamFactory podam, @NonNull ImmutableMap<Platform, DecryptedClient> clients) {
        return new Builder(podam, clients);
    }

    @RequiredArgsConstructor
    public static class Builder {
        private final @NonNull PodamFactory podamFactory;
        private final @NonNull ImmutableMap<Platform, DecryptedClient> clients;
        private final @NonNull Map<Platform, PlatformHelper.PlatformHelperBuilder> platformHelpers = new HashMap<>();

        public @NonNull OAuthHelper build() {
            final var map = platformHelpers.entrySet()
                    .stream()
                    .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, e -> e.getValue().build()));
            return new OAuthHelper(map);
        }

        public @NonNull Builder addDefaultIdentities() {
            return addTwitchIdentity().addDiscordIdentity();
        }

        private @NonNull Builder addPlatformHelper(@NonNull Platform platform) {
            final var userInfo = new UserInfo(podamFactory, platform);
            this.platformHelpers.computeIfAbsent(platform, p -> PlatformHelper.builder().client(clients.get(p))).userInfo(userInfo);
            return this;
        }

        public @NonNull Builder addTwitchIdentity() {
            return addPlatformHelper(Platform.TWITCH);
        }

        public @NonNull Builder addDiscordIdentity() {
            return addPlatformHelper(Platform.DISCORD);
        }
    }


}
