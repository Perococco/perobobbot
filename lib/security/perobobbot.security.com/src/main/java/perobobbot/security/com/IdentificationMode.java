package perobobbot.security.com;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.TypeScript;

@RequiredArgsConstructor
@TypeScript
public enum IdentificationMode implements IdentifiedEnum {
    PASSWORD("password"),
    OPEN_ID("openid"),
    ;

    @Getter
    private final @NonNull String identification;


}
