package perobobbot.discord.resources;

import lombok.NonNull;
import lombok.Value;
import perobobbot.oauth.UserIdentity;

import java.time.Instant;

@Value
public class AuthorizationInformation {
    @NonNull Object application;
    @NonNull String[] scopes;
    @NonNull Instant expires;
    @NonNull DiscordUser user;


}
