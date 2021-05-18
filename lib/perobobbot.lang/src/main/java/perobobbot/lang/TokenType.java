package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenType implements IdentifiedEnum {
    CLIENT_TOKEN("client_token"),
    USER_TOKEN("user_token"),
    ;

    @Getter
    private final @NonNull String identification;

    public <T> T accept(@NonNull Visitor<T> visitor) {
        return switch (this) {
            case CLIENT_TOKEN -> visitor.visitClientToken();
            case USER_TOKEN -> visitor.visitUserToken();
        };
    }

    public interface Visitor<T> {
        @NonNull T visitClientToken();
        @NonNull T visitUserToken();
    }


}


