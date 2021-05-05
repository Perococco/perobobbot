package perobobbot.oauth;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class UserOAuthInfo {

    /**
     * The URI to GET to start the user OAuth process
     */
    @Getter
    private final @NonNull URI oauthURI;

    /**
     * A completion stage that will contain the user token
     * if the OAuth process succeeded
     */
    @Getter
    private final @NonNull CompletionStage<Token> futureToken;
}
