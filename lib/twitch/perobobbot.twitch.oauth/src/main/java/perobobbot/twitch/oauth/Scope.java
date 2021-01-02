package perobobbot.twitch.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Scope {
    ANALYTICS_READ_EXTENSIONS("analytics:read:extensions", "View analytics data for your extensions."),
    ANALYTICS_READ_GAMES("analytics:read:games", "View analytics data for your games."),
    BITS_READ_TO_DELETE("bits:read:to_delete", "View Bits information for your channel."),
    CHANNEL_EDIT_COMMERCIAL("channel:edit:commercial", "Run commercials on a channel."),
    CHANNEL_MANAGE_BROADCAST("channel:manage:broadcast", "Manage your channel’s broadcast configuration, including updating channel configuration and managing stream markers and stream tags."),
    CHANNEL_MANAGE_EXTENSIONS("channel:manage:extensions", "Manage your channel’s extension configuration, including activating extensions."),
    CHANNEL_MANAGE_REDEMPTIONS("channel:manage:redemptions", "Manage Channel Points custom rewards and their redemptions on a channel."),
    CHANNEL_READ_HYPE_TRAIN("channel:read:hype_train", "Gets the most recent hype train on a channel."),
    CHANNEL_READ_REDEMPTIONS("channel:read:redemptions", "View Channel Points custom rewards and their redemptions on a channel."),
    CHANNEL_READ_STREAM_KEY("channel:read:stream_key", "Read an authorized user’s stream key."),
    CHANNEL_READ_SUBSCRIPTIONS("channel:read:subscriptions", "Get a list of all subscribers to your channel and check if a user is subscribed to your channel"),
    CLIPS_EDIT_TO_DELETE("clips:edit:to_delete", "Manage a clip object."),
    USER_EDIT_TO_DELETE("user:edit:to_delete", "Manage a user object."),
    USER_EDIT_FOLLOWS("user:edit:follows", "Edit your follows."),
    USER_READ_BROADCAST("user:read:broadcast", "View your broadcasting configuration, including extension configurations."),
    USER_READ_EMAIL("user:read:email", "Read an authorized user’s email address."),
    ;

    private final @NonNull String id;
    private final @NonNull String description;

}
