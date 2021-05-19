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

    /**
     * Used to send the URI to the front, or can be used in a graphical environment
     * with {@link java.awt.Desktop#browse(URI)} or in FX with WebView.
     */
    @NonNull Consumer1<URI> uriCallable;
}
