package perobobbot.discord.resources;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DiscordIdentity;
import perobobbot.lang.UserIdentity;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DiscordUser {
    @NonNull String id;//	the user's id	identify
    @NonNull String username;//	string	the user's username, not unique across the platform	identify
    @NonNull String discriminator;//	string	the user's 4-digit discord-tag	identify
    String avatar;//the user's avatar hash	identify
    Boolean bot;//	boolean	whether the user belongs to an OAuth2 application	identify
    Boolean system;//	boolean	whether the user is an Official Discord System user (part of the urgent message system)	identify
    Boolean mfaEnabled;//	boolean	whether the user has two factor enabled on their account	identify
    String banner;//	the user's banner hash	identify
    Integer accentColor;//	the user's banner color encoded as an integer representation of hexadecimal color code	identify
    String locale;//the user's chosen language option
    Boolean verified;//whether the email on this account has been verified	email
    String email;//the user's email	email
    Integer flags;//	integer	the flags on a user's account	identify
    Integer premiumType;//	integer	the type of Nitro subscription on a user's account	identify
    Integer publicFlags;//?	integer	the public flags on a user's account	identify


    public @NonNull UserIdentity toUserIdentity() {
        return new DiscordIdentity(id,username,discriminator);
    }

}
