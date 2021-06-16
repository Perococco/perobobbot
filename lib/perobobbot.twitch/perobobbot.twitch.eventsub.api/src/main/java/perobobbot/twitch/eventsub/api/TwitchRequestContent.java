package perobobbot.twitch.eventsub.api;

import lombok.NonNull;

import java.time.Instant;

public record TwitchRequestContent<T>(@NonNull String type,
                                   @NonNull String messageId,
                                   @NonNull Instant timeStamp,
                                   @NonNull T content) {

    public <U> @NonNull TwitchRequestContent<U> with(@NonNull U newContent) {
        return new TwitchRequestContent<>(type,messageId,timeStamp,newContent);
    }

}
