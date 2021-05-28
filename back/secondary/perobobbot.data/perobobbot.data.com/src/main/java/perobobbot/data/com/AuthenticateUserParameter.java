package perobobbot.data.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Scope;

@Value
public class AuthenticateUserParameter {

    @NonNull String login;
    @NonNull ImmutableSet<? extends Scope> scopes;
}
