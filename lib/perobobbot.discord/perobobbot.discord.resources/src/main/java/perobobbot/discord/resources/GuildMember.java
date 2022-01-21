package perobobbot.discord.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GuildMember {
    DiscordUser discordUser;//	user object	the user this guild member represents
    String nick;// this user's guild nickname
    String avatar;//	the member's guild avatar hash
    String[] roles;//	array of snowflakes	array of role object ids
    @NonNull Instant joinedAt;//	ISO8601 timestamp	when the user joined the guild
    Instant premiumSince;//?	?ISO8601 timestamp	when the user started boosting the guild
    boolean deaf;//	boolean	whether the user is deafened in voice channels
    boolean mute;//	boolean	whether the user is muted in voice channels
    Boolean pending;//	boolean	whether the user has not yet passed the guild's Membership Screening requirements
    String permissions;//	total permissions of the member in the channel, including overwrites, returned when in the interaction object
    Instant communicationDisabledUntil;// ?	?ISO8601 timestamp	when the user's timeout will expire and the user will be able to communicate in the guild again, null or a time in the past if the user is not timed out
}
