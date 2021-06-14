package perobobbot.twitch.eventsub.api.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum PredicationStatus implements IdentifiedEnum {
    RESOLVED("resolved"),
    CANCELED("canceled")
    ;
    @Getter
    private final @NonNull String identification;
}
