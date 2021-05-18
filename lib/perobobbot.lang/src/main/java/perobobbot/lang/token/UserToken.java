package perobobbot.lang.token;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.lang.Scope;

public interface UserToken<T> extends Token<T> {

    @NonNull T getRefreshToken();
    @NonNull ImmutableSet<? extends Scope> getScopes();

}
