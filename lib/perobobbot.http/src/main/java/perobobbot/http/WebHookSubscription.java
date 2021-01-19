package perobobbot.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.lang.Subscription;

import java.net.URI;

@RequiredArgsConstructor
public class WebHookSubscription implements Subscription {

    /**
     * the callback to use for the webhooks
     */
    private final @NonNull URI webHookCallbackURI;

    /**
     * the callback to use for the oauth flow
     */
    private final @NonNull URI oauthCallbackURI;

    @Delegate
    private final @NonNull Subscription subscription;
}
