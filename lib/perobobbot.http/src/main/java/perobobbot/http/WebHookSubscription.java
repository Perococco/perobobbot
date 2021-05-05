package perobobbot.http;

import lombok.Getter;
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
    @Getter
    private final @NonNull URI webHookCallbackURI;

    /**
     * the callback to use for the oauth flow
     */
    @Getter
    private final @NonNull URI oauthCallbackURI;

    @Delegate
    private final @NonNull Subscription subscription;
}
