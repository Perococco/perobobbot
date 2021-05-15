package perobobbot.server;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.Token;

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneOffset;
import java.util.concurrent.ExecutionException;

@Log4j2
@Component
@RequiredArgsConstructor
public class PerococcoRunner implements ApplicationRunner {

    public static final String CLIENT_ID = "m01e1fb0emhtr0toc6eydvl9zkuecu";

    public final @NonNull OAuthManager oAuthManager;

    private final Scope scope1 = () -> "moderation:read";
    private final Scope scope2 = () -> "bits:read";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final var oauthInfo = oAuthManager.getController(Platform.TWITCH)
                                          .prepareUserOAuth(CLIENT_ID, readSecret(), ImmutableSet.of(scope1,scope2));

        Desktop.getDesktop().browse(oauthInfo.getOauthURI());

        try {
            final var token = oauthInfo.getFutureToken().toCompletableFuture().get();
            handleToken(token);
        } catch (Throwable t) {
            System.err.println("Oauth fail " + t.getMessage());
            t.printStackTrace();
        }
    }

    private void handleToken(@NonNull Token token) throws ExecutionException, InterruptedException {
        System.out.println("Type            = " + token.getTokenType());
        System.out.println("Scopes          = " + token.getScopes());
        System.out.println("Duration        = " + token.getDuration());
        System.out.println("Expiration Time = " + token.getExpirationInstant().atZone(ZoneOffset.UTC));
        try {
            Files.writeString(Path.of("/home/perococco/token.txt"), token.getAccessToken());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        final var oAuthController = oAuthManager.getController(Platform.TWITCH);

        final var validation = oAuthController.validateToken(token).toCompletableFuture().get();
        System.out.println("Validation : "+validation);

        final var result = oAuthController.revokeToken(CLIENT_ID, token.getAccessToken()).toCompletableFuture().get();

        System.out.println("revoke: "+result);
    }


    private Secret readSecret() throws IOException {
        final String value = Files.readAllLines(Path.of("/home/perococco/twitch_keys/perobobbot_app_secret.txt")).get(
                0);
        return Secret.with(value);
    }
}
