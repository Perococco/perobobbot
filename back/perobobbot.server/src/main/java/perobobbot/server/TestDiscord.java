package perobobbot.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.lang.Platform;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.OAuthManager;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class TestDiscord {

    private final @NonNull
    OAuthManager oAuthManager;

    private final @NonNull
    @UnsecuredService
    ClientService clientService;

    private final @NonNull
    @UnsecuredService
    OAuthService oAuthService;

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
        try {
           deleteAllTokens(d -> d.getPlatform() == Platform.DISCORD);
//           createUserToken(Platform.DISCORD);
//            final var tokens = oAuthService.getAllUserTokens("perococco");
//            for (DecryptedUserTokenView token : tokens) {
//                oAuthService.deleteUserToken(token.getId());
//            }
//            new ClientUpdater(clientService, Path.of("/home/perococco/perobobbot_clients.txt")).update();

//            final var client = clientService.getClient(Platform.DISCORD);
//            final var controller = new OAuthControllerFromManager(oAuthManager,Platform.DISCORD);
//
//
//            final var token = tokens.stream().filter(p -> p.getPlatform() == Platform.DISCORD).findFirst();
//
//            if (token.isEmpty()) {
//                final var perococco = oAuthService.createUserToken("perococco", Platform.DISCORD);
//                Desktop.getDesktop().browse(perococco.getOauthURI());
//                final var decryptedUserTokenView = perococco.getFutureToken().toCompletableFuture().get();
//                System.out.println("OK "+ decryptedUserTokenView.getOwnerLogin());
//            } else {
//                oAuthService.deleteUserToken(token.get().getId());
//            }
//

        } catch (Throwable t) {
            System.out.println("Failed");
            dumpError(t);
        }
    }

    private void dumpError(@NonNull Throwable throwable) throws IOException {
        try (PrintStream out = new PrintStream(Files.newOutputStream(Path.of("/home/perococco/discord_auth_error.txt")))) {
            throwable.printStackTrace(out);
        }
    }

    private void deleteAllTokens(@NonNull Predicate<DecryptedUserTokenView> tokenFilter) {
        final var tokens = oAuthService.getAllUserTokens("perococco");
        for (DecryptedUserTokenView token : tokens) {
            if (tokenFilter.test(token)) {
                oAuthService.deleteUserToken(token.getId());
            }
        }
    }

    private void createUserToken(@NonNull Platform platform) throws ExecutionException, InterruptedException, IOException {
        final var oauthInfo = oAuthService.createUserToken("perococco", platform);
        Desktop.getDesktop().browse(oauthInfo.getOauthURI());

        oauthInfo.get();
    }
}
