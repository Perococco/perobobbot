package perobobbot.server;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.IO;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.*;
import perobobbot.eventsub.UserEventSubManager;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.token.DecryptedClientTokenView;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.OAuthUrlOptions;
import perobobbot.oauth.Token;
import perobobbot.oauth.UserOAuthInfo;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.channelpoints.CreateCustomRewardParameter;
import perobobbot.twitch.client.api.channelpoints.CustomReward;
import reactor.core.publisher.Mono;

import javax.swing.plaf.DesktopPaneUI;
import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletionStage;

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
//            new ClientUpdater(clientService, Path.of("/home/perococco/perobobbot_clients.txt")).update();

            final var client = clientService.getClient(Platform.DISCORD);
            final var controller = oAuthManager.getController(Platform.DISCORD);

            final var tokens = oAuthService.getAllUserTokens("perococco");

            final var token = tokens.stream().filter(p -> p.getPlatform() == Platform.DISCORD).findFirst();

            if (token.isEmpty()) {
                final var perococco = oAuthService.createUserToken("perococco", Platform.DISCORD);
                Desktop.getDesktop().browse(perococco.getOauthURI());
                final var decryptedUserTokenView = perococco.getFutureToken().toCompletableFuture().get();
                System.out.println("OK "+ decryptedUserTokenView.getOwnerLogin());
            } else {
                oAuthService.deleteUserToken(token.get().getId());
            }


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
}
