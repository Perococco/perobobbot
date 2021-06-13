package perobobbot.twitch.eventsub.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum PollStatus implements IdentifiedEnum {
    COMPLETED("completed"),
    ARCHIVED("archived"),
    TERMINATED("terminated"),
    ;

    @Getter
    private final @NonNull String identification;
}
