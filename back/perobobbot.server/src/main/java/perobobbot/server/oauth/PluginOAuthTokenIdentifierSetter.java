package perobobbot.server.oauth;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.lang.PluginService;
import perobobbot.lang.fp.Function0;
import perobobbot.oauth.OAuthContext;
import perobobbot.oauth.OAuthContextHolder;
import perobobbot.oauth.OAuthTokenIdentifierSetter;
import perobobbot.oauth.TokenIdentifier;

@Component
@PluginService(type = OAuthTokenIdentifierSetter.class, apiVersion = OAuthTokenIdentifierSetter.VERSION, sensitive = false)
public class PluginOAuthTokenIdentifierSetter implements OAuthTokenIdentifierSetter {

    @Override
    public void setTokenIdentifier(@NonNull TokenIdentifier tokenIdentifier) {
        OAuthContextHolder.getContext().setTokenIdentifier(tokenIdentifier);
    }

    @Override
    public <T> @NonNull T wrapCall(@NonNull TokenIdentifier tokenIdentifier, @NonNull Function0<T> call) {
        return OAuthContextHolder.getContext().wrapCall(tokenIdentifier, call);
    }
}
