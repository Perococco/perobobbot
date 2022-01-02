package perobobbot.lang.token;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Scope;

public interface UserToken<T> extends Token<T> {

    /**
     * @return the token value to use to refresh this token
     */
    @NonNull T getRefreshToken();

    /**
     * @return the scopes this token authorizes
     */
    @NonNull ImmutableSet<? extends Scope> getScopes();

}
