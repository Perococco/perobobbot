package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

public interface TokenIdentifier  {

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> extends Function1<TokenIdentifier,T> {

        @NonNull T visit(@NonNull ChatTokenIdentifier tokenIdentifier);
        @NonNull T visit(@NonNull LoginTokenIdentifier tokenIdentifier);

        @Override
        @NonNull
        default T f(@NonNull TokenIdentifier tokenIdentifier) {
            return tokenIdentifier.accept(this);
        }
    }

}
