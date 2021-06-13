package perobobbot.twitch.eventsub.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum Tier implements IdentifiedEnum {
    TIER_1("1000"),
    TIER_2("2000"),
    TIER_3("3000"),
    ;

    @Getter
    private final @NonNull String identification;
}
