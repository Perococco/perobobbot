package perobobbot.twitch.eventsub.api.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum OutcomeColor implements IdentifiedEnum {
    PINK("pink"),
    BLUE("blue"),
    ;
    @Getter
    private final @NonNull String identification;
}
