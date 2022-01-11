package perobobbot.lang;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.token.UserTokenRefresher;

import java.util.Optional;
import java.util.UUID;


@Builder(toBuilder = true,builderClassName = "Builder")
@Value
@EqualsAndHashCode(exclude = {"refresher"})
public class ChatConnectionInfo {

    @JsonIgnore
    UserTokenRefresher refresher;

    @NonNull UUID botId;
    @NonNull String botName;
    @NonNull UUID platformUserId;
    @NonNull Platform platform;

    /**
     * @return the nick to use to connect
     */
    @JsonIgnore
    @NonNull String nick;
    @JsonIgnore
    @NonNull Secret secret;

    public boolean isRefreshable() {
        return refresher != null;
    }

    public @NonNull Optional<ChatConnectionInfo> refresh() {
        if (refresher == null) {
            return Optional.empty();
        }
        final var token = refresher.getRefreshedUserToken();
        return Optional.ofNullable(toBuilder().secret(token.getAccessToken()).build());
    }


}
