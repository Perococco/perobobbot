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
public class Message implements GatewayEvent {

    @NonNull String id;//	id of the message
    @NonNull String channelId;//	snowflake	id of the channel the message was sent in
    String guild_id;//	snowflake	id of the guild the message was sent in
    DiscordUser author;//	user object	the author of this message (not guaranteed to be a valid user, see below)
    GuildMember member;//	partial guild member object	member properties for this message's author
    @NonNull String content;//	string	contents of the message
    @NonNull Instant timestamp;//	ISO8601 timestamp	when this message was sent
    Instant editedTimestamp;//	?ISO8601 timestamp	when this message was edited (or null if never)
    boolean tts;//	whether this was a TTS message
    boolean mentionEveryone;//	boolean	whether this message mentions everyone
    @NonNull DiscordUser[] mentions;//	array of user objects, with an additional partial member field	users specifically mentioned in the message
    @NonNull String[] mentionRoles;//	array of role object ids	roles specifically mentioned in this message
//    mention_channels?****	array of channel mention objects	channels specifically mentioned in this message
//    attachments	array of attachment objects	any attached files
//    embeds	array of embed objects	any embedded content
//    reactions?	array of reaction objects	reactions to the message
//    nonce?	integer or string	used for validating a message was sent
//    pinned	boolean	whether this message is pinned
//    webhook_id?	snowflake	if the message is generated by a webhook, this is the webhook's id
//    type	integer	type of message
//    activity?	message activity object	sent with Rich Presence-related chat embeds
//    application?	partial application object	sent with Rich Presence-related chat embeds
//    application_id?	snowflake	if the message is an Interaction or application-owned webhook, this is the id of the application
//    message_reference?	message reference object	data showing the source of a crosspost, channel follow add, pin, or reply message
//    flags?	integer	message flags combined as a bitfield
//    referenced_message?*****	?message object	the message associated with the message_reference
//    interaction?	message interaction object	sent if the message is a response to an Interaction
//    thread?	channel object	the thread that was started from this message, includes thread member object
//    components?	Array of message components	sent if the message contains components like buttons, action rows, or other interactive components
//    sticker_items?	array of message sticker item objects	sent if the message contains stickers
//    stickers?	array of sticker objects	Deprecated the stickers sent with the message

}
