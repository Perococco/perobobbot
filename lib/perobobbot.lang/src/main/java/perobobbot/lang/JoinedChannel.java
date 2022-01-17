package perobobbot.lang;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JoinedChannel {

    private final @NonNull UUID id;

    private final @NonNull PlatformBot platformBot;

    @Getter(AccessLevel.NONE)
    private final DecryptedTokenInfo decryptedTokenInfo;

    private final @NonNull String channelId;


    public @NonNull UUID getBotId() {
        return platformBot.getBot().getId();
    }

    public @NonNull Optional<DecryptedTokenInfo> getDecryptedTokenInfo() {
        return Optional.ofNullable(decryptedTokenInfo);
    }

    public @NonNull String getBotName() {
        return platformBot.getBot().getName();
    }

    public @NonNull UUID getTwitchUserId() {
        return platformBot.getUser().getId();
    }

    public @NonNull String getNick() {
        return platformBot.getUser().getLogin();
    }

}
