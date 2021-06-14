package perobobbot.twutch.eventsub.manager;

import lombok.NonNull;

public record TwitchRequestContent<T>(@NonNull String type, @NonNull T content) {

}
