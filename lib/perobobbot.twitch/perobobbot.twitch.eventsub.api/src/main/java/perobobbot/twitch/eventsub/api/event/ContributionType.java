package perobobbot.twitch.eventsub.api.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum ContributionType implements IdentifiedEnum {
    BITS("bits"),
    SUBSCRIPTION("subscription")
    ;
    @Getter
    private final @NonNull String identification;
}
