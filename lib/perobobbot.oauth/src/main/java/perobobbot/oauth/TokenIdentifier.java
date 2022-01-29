package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;

/**
 * A token identifier contains some information that can be used to retrieve
 * the correct token when performing secured operation.
 * The {@link OAuthContextHolder} is initialized (for each thread) with the correct token identifier. The type
 * of the identifier depends on what triggered the action. For instance, if the action has been triggered by
 * a call to the bot Rest API, the token identifier is set to a {@link LoginTokenIdentifier} that contains the login of the identified user.
 * If the action has been triggered by a chat command, the token identifier will be set to a {@link ChatTokenIdentifier} that contains all the
 * information to identify the user that executed the chat command.
 */
public sealed interface TokenIdentifier permits ChatTokenIdentifier, LoginTokenIdentifier, BroadcasterIdentifier  {

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    interface Visitor<T> extends Function1<TokenIdentifier,T> {

        @NonNull T visit(@NonNull ChatTokenIdentifier tokenIdentifier);
        @NonNull T visit(@NonNull LoginTokenIdentifier tokenIdentifier);
        @NonNull T visit(@NonNull BroadcasterIdentifier tokenIdentifier);

        @Override
        @NonNull
        default T f(@NonNull TokenIdentifier tokenIdentifier) {
            return tokenIdentifier.accept(this);
        }
    }

}
