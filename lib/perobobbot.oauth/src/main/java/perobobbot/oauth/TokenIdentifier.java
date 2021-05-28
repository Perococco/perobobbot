package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

public interface TokenIdentifier  {

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> {

        default @NonNull Function1<TokenIdentifier,T> asFunction() {
            return t -> t.accept(this);
        }

        @NonNull T visit(@NonNull ChatTokenIdentifier tokenIdentifier);

        @NonNull T visit(@NonNull LoginTokenIdentifier tokenIdentifier);
    }

}
