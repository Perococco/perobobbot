package perobobbot.discord.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NonNull;
import lombok.Value;
import perobobbot.oauth.UserIdentity;

/**
 *
 */
@Value
@JsonIgnoreProperties
public class DiscordUser {

    // user's id	identify
    @NonNull String id;
    // the user's username, not unique across the platform	identify
    @NonNull String username;
    //the user's 4-digit discord-tag	identify
    @NonNull String discriminator;
    //the user's avatar hash	identify
    String avatar;
    //	whether the user belongs to an OAuth2 application	identify
    Boolean bot;
    Boolean system;//	whether the user is an Official Discord System user (part of the urgent message system)	identify
    Boolean mfa_enabled;//	whether the user has two factor enabled on their account	identify
    String banner;//	the user's banner hash	identify
    Integer accent_color;//	the user's banner color encoded as an integer representation of hexadecimal color code	identify
    String locale;//	the user's chosen language option	identify
    Boolean verified;//	whether the email on this account has been verified	email
    String email;//	the user's email	email
    Integer flags;//	the flags on a user's account	identify
    Integer premium_type;//	the type of Nitro subscription on a user's account	identify
    Integer public_flags;//	the public flags on a user's account	identify


    public @NonNull UserIdentity toUserIdentity() {
        return new UserIdentity(id,username+"#"+discriminator);
    }

}
