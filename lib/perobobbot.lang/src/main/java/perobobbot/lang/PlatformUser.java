package perobobbot.lang;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import lombok.NonNull;

import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "platform")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DiscordUser.class, name="Discord"),
        @JsonSubTypes.Type(value = TwitchUser.class, name="Twitch"),
})
@TypeScript
public sealed interface PlatformUser permits DiscordUser, TwitchUser {

    @NonNull Platform getPlatform();

    @NonNull UUID getId();

    @NonNull String getUserId();

    @NonNull String getLogin();

    @NonNull String getPseudo();

    default @NonNull String prettyPrint() {
        return getPlatform().getIdentification()+":"+getPseudo();
    }
}
