package perobobbot.twitch.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnumWithAlternateIdentification;

@RequiredArgsConstructor
public enum RewardRedemptionStatus implements IdentifiedEnumWithAlternateIdentification {
    UNKNOWN("unknown","UNKNOWN"),
    UNFULFILLED("unfulfilled","UNFULFILLED"),
    FULFILLED("fulfilled","FULFILLED"),
    CANCELED("canceled","CANCELED")
    ;
    @Getter
    private final @NonNull String identification;
    @Getter
    private final @NonNull String alternateIdentification;
}
