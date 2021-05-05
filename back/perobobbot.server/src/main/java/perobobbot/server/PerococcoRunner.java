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

import java.awt.*;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Log4j2
@Component
@RequiredArgsConstructor
public class PerococcoRunner implements ApplicationRunner {

    public static final String CLIENT_ID = "m01e1fb0emhtr0toc6eydvl9zkuecu";

    public final @NonNull OAuthManager oAuthManager;

    private final Scope scope = () -> "moderation:read";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final var oauthInfo = oAuthManager.getController(Platform.TWITCH)
                    .orElseThrow()
                    .prepareUserOAuth(CLIENT_ID, readSecret(), ImmutableSet.of(scope));

        Desktop.getDesktop().browse(oauthInfo.getOauthURI());

        oauthInfo.getFutureToken().whenComplete((result,error) -> {
            if (error != null) {
                System.out.println("Fail to get OAuth token");
                error.printStackTrace();
            } else {
                System.out.println("Type       = "+ result.getTokenType());
                System.out.println("Scopes     = "+ Arrays.toString(result.getScopes()));
                System.out.println("Expires in = "+ result.getExpiresIn());
                try {
                    Files.writeString(Path.of("/home/perococco/token.txt"),result.getAccessToken());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        });


    }

    private Secret readSecret() throws IOException {
        final String value = Files.readAllLines(Path.of("/home/perococco/twitch_keys/perobobbot_app_secret.txt")).get(0);
        return Secret.with(value);
    }
}
