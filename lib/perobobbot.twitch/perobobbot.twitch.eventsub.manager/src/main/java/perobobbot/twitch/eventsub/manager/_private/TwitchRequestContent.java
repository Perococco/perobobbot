package perobobbot.twitch.eventsub.manager._private;

import lombok.NonNull;

import java.time.Instant;

public record TwitchRequestContent(@NonNull String type,
                                   @NonNull String messageId,
                                   @NonNull Instant timeStamp,
                                   @NonNull byte[] content) {

}
