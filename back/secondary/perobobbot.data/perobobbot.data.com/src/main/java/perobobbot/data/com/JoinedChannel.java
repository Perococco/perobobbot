package perobobbot.data.com;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.ViewerIdentity;
import perobobbot.lang.token.BaseUserToken;
import perobobbot.lang.token.DecryptedUserToken;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JoinedChannel {

    private final @NonNull UUID id;

    private final @NonNull Bot bot;

    private final @NonNull ViewerIdentity viewerIdentity;

    @Getter(AccessLevel.NONE)
    private final DecryptedUserToken decryptedUserToken;

    private final @NonNull String channelName;



    public @NonNull UUID getBotId() {
        return bot.getId();
    }

    public @NonNull Optional<DecryptedUserToken> getDecryptedUserToken() {
        return Optional.ofNullable(decryptedUserToken);
    }

    public @NonNull String getBotName() {
        return bot.getName();
    }

    public @NonNull Platform getPlatform() {
        return viewerIdentity.getPlatform();
    }

    public @NonNull UUID getViewerIdentityId() {
        return viewerIdentity.getId();
    }

    public @NonNull String getNick() {
        return viewerIdentity.getLogin();
    }
    public @NonNull Optional<Secret> getSecret() {
        return getDecryptedUserToken().map(BaseUserToken::getAccessToken);
    }

}
