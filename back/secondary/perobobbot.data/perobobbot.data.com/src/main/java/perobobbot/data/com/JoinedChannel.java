package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.*;
import perobobbot.lang.token.DecryptedUserToken;
import perobobbot.lang.token.DecryptedUserTokenView;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class JoinedChannel {

    private final @NonNull UUID id;

    private final @NonNull Bot bot;

    private final @NonNull ViewerIdentity viewerIdentity;

    private final DecryptedUserToken decryptedUserToken;

    private final @NonNull String channelName;

    public @NonNull Optional<ChatConnectionInfo> createChatConnectionInfo() {
        if (decryptedUserToken == null) {
            return Optional.empty();
        }
        return Optional.of(new ChatConnectionInfo(bot.getId(),
                viewerIdentity.getId(),
                viewerIdentity.getPlatform(),
                bot.getName(),
                viewerIdentity.getLogin(),
                decryptedUserToken.getAccessToken()));
    }

}
