package perobobbot.twitch.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum RewardRedemptionStatus implements IdentifiedEnum {
    UNKNOWN("unknown"),
    UNFULFILLED("unfulfilled"),
    FULFILLED("fulfilled"),
    CANCELED("canceled")
    ;
    @Getter
    private final @NonNull String identification;
}
