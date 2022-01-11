package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.DecryptedTokenInfo;
import perobobbot.lang.JoinedTwitchChannel;
import perobobbot.lang.Platform;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.lang.token.UserTokenRefresher;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ChatConnectionHelper {

    private final @NonNull OAuthService oAuthService;

    public @NonNull Optional<ChatConnectionInfo> createNotRefreshable(@NonNull JoinedTwitchChannel joinedTwitchChannel) {
        final var builder = createAndFill(joinedTwitchChannel);
        return joinedTwitchChannel.getDecryptedTokenInfo()
                                  .map(DecryptedTokenInfo::getAccessToken)
                                  .map(builder::secret)
                                  .map(ChatConnectionInfo.Builder::build);
    }

    public @NonNull Optional<ChatConnectionInfo> createRefreshable(@NonNull JoinedTwitchChannel joinedTwitchChannel) {
        final var token = joinedTwitchChannel.getDecryptedTokenInfo().orElse(null);
        if (token == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(createAndFill(joinedTwitchChannel)
                .secret(token.getAccessToken())
                .refresher(new Refresher(token.getTokenId(), oAuthService))
                .build());
    }


    private @NonNull ChatConnectionInfo.Builder createAndFill(@NonNull JoinedTwitchChannel joinedTwitchChannel) {
        return ChatConnectionInfo.builder()
                                 .botId(joinedTwitchChannel.getBotId())
                                 .botName(joinedTwitchChannel.getBotName())
                                 .platformUserId(joinedTwitchChannel.getTwitchUserId())
                                 .platform(Platform.TWITCH)
                                 .nick(joinedTwitchChannel.getNick());
    }


    @RequiredArgsConstructor
    private static class Refresher implements UserTokenRefresher {
        private final @NonNull UUID tokenId;
        private final @NonNull OAuthService oAuthService;

        @Override
        public @NonNull DecryptedUserTokenView getRefreshedUserToken() {
            try {
                return oAuthService.refreshUserToken(tokenId);
            } catch (Throwable t) {
                t.printStackTrace();
                throw t;
            }
        }
    }
}
