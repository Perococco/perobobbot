package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;

@RequiredArgsConstructor
public enum PointType implements IdentifiedEnum {
    CREDIT("credit"),
    ;

    @Getter
    private final @NonNull String identification;

}
