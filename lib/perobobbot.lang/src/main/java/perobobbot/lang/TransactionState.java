package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionState implements IdentifiedEnum {
    PENDING("pending"),
    CANCELLED("cancelled"),
    COMPLETED("completed"),
    DETACHED("detached"),
    ;

    @Getter
    private final @NonNull String identification;
}
