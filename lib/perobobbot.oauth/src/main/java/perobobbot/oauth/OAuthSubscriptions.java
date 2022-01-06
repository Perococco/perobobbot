package perobobbot.oauth;

import lombok.NonNull;
import perobobbot.http.WebHookManager;
import perobobbot.lang.Instants;
import perobobbot.oauth.spring.DefaultOAuthSubscriptions;

public interface OAuthSubscriptions {

    static @NonNull OAuthSubscriptions create(@NonNull Instants instants, @NonNull WebHookManager webHookManager) {
        return new DefaultOAuthSubscriptions(instants, webHookManager);
    }

    @NonNull OauthSubscriptionData subscribe(@NonNull String path, @NonNull OAuthListener oAuthListener);

    void removeAll();
}
