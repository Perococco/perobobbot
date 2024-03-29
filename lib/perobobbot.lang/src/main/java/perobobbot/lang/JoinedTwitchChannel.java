package perobobbot.lang;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JoinedTwitchChannel {

    private final @NonNull UUID id;

    private final @NonNull Bot bot;

    private final @NonNull TwitchUser twitchUser;

    @Getter(AccessLevel.NONE)
    private final DecryptedTokenInfo decryptedTokenInfo;

    private final @NonNull String channelName;


    public @NonNull UUID getBotId() {
        return bot.getId();
    }

    public @NonNull Optional<DecryptedTokenInfo> getDecryptedTokenInfo() {
        return Optional.ofNullable(decryptedTokenInfo);
    }

    public @NonNull String getBotName() {
        return bot.getName();
    }

    public @NonNull UUID getTwitchUserId() {
        return twitchUser.getId();
    }

    public @NonNull String getNick() {
        return twitchUser.getLogin();
    }

}
