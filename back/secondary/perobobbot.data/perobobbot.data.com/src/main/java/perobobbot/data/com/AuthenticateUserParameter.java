package perobobbot.data.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Scope;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.TryConsumer1;

import java.net.URI;

@Value
public class AuthenticateUserParameter {

    @NonNull String login;
    @NonNull ImmutableSet<? extends Scope> scopes;
}
