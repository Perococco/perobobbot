package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer0;
import perobobbot.lang.fp.Function0;
import perobobbot.lang.fp.Function1;

public interface OAuthTokenIdentifierSetter {

    int VERSION = 1;

    void setTokenIdentifier(@NonNull TokenIdentifier tokenIdentifier);

    <T> @NonNull T wrapCall(@NonNull TokenIdentifier tokenIdentifier, @NonNull Function0<T> call);

    default void wrapRun(@NonNull TokenIdentifier tokenIdentifier, @NonNull Consumer0 call) {
        wrapCall(tokenIdentifier,call.asFunction());
    }

    default <A,T> @NonNull T wrapCall(@NonNull TokenIdentifier tokenIdentifier, @NonNull Function1<? super A,? extends T> call, @NonNull A parameter) {
        return wrapCall(tokenIdentifier, call.f1(parameter));
    }
}
