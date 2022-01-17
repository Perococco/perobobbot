package perobobbot.discord.oauth.api;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    CREATE_INSTANT_INVITE("*", 0L, "Allows creation of instant invites", "T, V, S"),
    KICK_MEMBERS("*", (1L << 1), "Allows kicking members", ""),
    BAN_MEMBERS("*", (1L << 2), "Allows banning members", ""),
    ADMINISTRATOR("*", (1L << 3), "Allows all permissions and bypasses channel permission overwrites", ""),
    MANAGE_CHANNELS("*", (1L << 4), "Allows management and editing of channels", "T, V, S"),
    MANAGE_GUILD("*", (1L << 5), "Allows management and editing of the guild", ""),
    ADD_REACTIONS("", (1L << 6), "Allows for the addition of reactions to messages", "T"),
    VIEW_AUDIT_LOG("", (1L << 7), "Allows for viewing of audit logs", ""),
    PRIORITY_SPEAKER("", (1L << 8), "Allows for using priority speaker in a voice channel", "V"),
    STREAM("", (1L << 9), "Allows the user to go live", "V"),
    VIEW_CHANNEL("", (1L << 10), "Allows guild members to view a channel, which includes reading messages in text channels", "T, V, S"),
    SEND_MESSAGES("", (1L << 11), "Allows for sending messages in a channel (does not allow sending messages in threads)", "T"),
    SEND_TTS_MESSAGES("", (1L << 12), "Allows for sending of /tts messages", "T"),
    MANAGE_MESSAGES("*", (1L << 13), "Allows for deletion of other users messages", "T"),
    EMBED_LINKS("*", (1L << 14), "Links sent by users with this permission will be auto-embedded", "T"),
    ATTACH_FILES("", (1L << 15), "Allows for uploading images and files", "T"),
    READ_MESSAGE_HISTORY("", (1L << 16), "Allows for reading of message history", "T"),
    MENTION_EVERYONE("", (1L << 17), "Allows for using the @everyone tag to notify all users in a channel, and the @here tag to notify all online users in a channel", "T"),
    USE_EXTERNAL_EMOJIS("", (1L << 18), "Allows the usage of custom emojis from other servers", "T"),
    VIEW_GUILD_INSIGHTS("", (1L << 19), "Allows for viewing guild insights", ""),
    CONNECT("", (1L << 20), "Allows for joining of a voice channel", "V, S"),
    SPEAK("", (1L << 21), "Allows for speaking in a voice channel", "V"),
    MUTE_MEMBERS("", (1L << 22), "Allows for muting members in a voice channel", "V, S"),
    DEAFEN_MEMBERS("", (1L << 23), "Allows for deafening of members in a voice channel", "V, S"),
    MOVE_MEMBERS("", (1L << 24), "Allows for moving of members between voice channels", "V, S"),
    USE_VAD("", (1L << 25), "Allows for using voice-activity-detection in a voice channel", "V"),
    CHANGE_NICKNAME("", (1L << 26), "Allows for modification of own nickname", ""),
    MANAGE_NICKNAMES("", (1L << 27), "Allows for modification of other users nicknames", ""),
    MANAGE_ROLES("*", (1L << 28), "Allows management and editing of roles", "T, V, S"),
    MANAGE_WEBHOOKS("*", (1L << 29), "Allows management and editing of webhooks", "T"),
    MANAGE_EMOJIS_AND_STICKERS("*", (1L << 30), "Allows management and editing of emojis and stickers", ""),
    USE_APPLICATION_COMMANDS("", (1L << 31), "Allows members to use application commands, including slash commands and context menu commands", "T"),
    REQUEST_TO_SPEAK("", (1L << 32), "Allows for requesting to speak in stage channels. (This permission is under active development and may be changed or removed.)", "S"),
    MANAGE_EVENTS("", (1L << 33), "Allows for creating, editing, and deleting scheduled events", "V, S"),
    MANAGE_THREADS("*", (1L << 34), "Allows for deleting and archiving threads, and viewing all private threads", "T"),
    CREATE_PUBLIC_THREADS("", (1L << 35), "Allows for creating threads", "T"),
    CREATE_PRIVATE_THREADS("", (1L << 36), "Allows for creating private threads", "T"),
    USE_EXTERNAL_STICKERS("", (1L << 37), "Allows the usage of custom stickers from other servers", "T"),
    SEND_MESSAGES_IN_THREADS("", (1L << 38), "Allows for sending messages in threads", "T"),
    START_EMBEDDED_ACTIVITIES("", (1L << 39), "Allows for launching activities (applications with the EMBEDDED flag) in a voice channel", "V"),
    MODERATE_MEMBERS("**", (1L << 40), "Allows for timing out users to prevent them from sending or reacting to messages in chat and threads, and from speaking in voice and stage channels", ""),
    ;
    private @NonNull
    final String remarks;
    private final long mask;
    private @NonNull
    final String description;
    private @NonNull
    final String channelType;

    public static @NonNull String computeQueryParam(Permission... permissions) {
        return computeQueryParam(ImmutableSet.copyOf(permissions));
    }

    public static @NonNull String computeQueryParam(ImmutableCollection<Permission> permissions) {
        return String.valueOf(permissions.stream()
                                         .mapToLong(p -> p.mask)
                                         .reduce(0, (m1, m2) -> m1 | m2));
    }

}
