package perobobbot.discord.resources;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NonNull;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Role {

    @NonNull String id;//	snowflake	role id
    @NonNull String name;//	string	role name
    int color;//integer representation of hexadecimal color code
    boolean hoist;//	if this role is pinned in the user listing
    String icon;//role icon hash
    String unicodeEmoji;//?	?string	role unicode emoji
    int position;//	integer	position of this role
    @NonNull String permissions;//	string	permission bit set
    boolean managed;//	boolean	whether this role is managed by an integration
    boolean mentionable;//	boolean	whether this role is mentionable
    RoleTag tags;//
}
