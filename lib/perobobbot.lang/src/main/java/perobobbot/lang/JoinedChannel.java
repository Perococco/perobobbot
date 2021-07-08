package perobobbot.lang;

import lombok.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JoinedChannel {

    private final @NonNull UUID id;

    private final @NonNull Bot bot;

    private final @NonNull ViewerIdentity viewerIdentity;

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

    public @NonNull Platform getPlatform() {
        return viewerIdentity.getPlatform();
    }

    public @NonNull UUID getViewerIdentityId() {
        return viewerIdentity.getId();
    }

    public @NonNull String getNick() {
        return viewerIdentity.getLogin();
    }

}
