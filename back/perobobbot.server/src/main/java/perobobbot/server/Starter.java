package perobobbot.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.chat.core.IO;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.service.*;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.server.config.io.ChatPlatformPluginManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Starter {

    private final @NonNull
    @UnsecuredService
    ClientService clientService;

    private final @NonNull
    @UnsecuredService
    BotService botService;

    private final @NonNull
    @UnsecuredService
    OAuthService oAuthService;

    private final @NonNull IO io;


    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
//        createClient();
//        joinChannel();
        WebClient webClient = WebClient.create();
        final var client = clientService.getClient(Platform.TWITCH);
        final var token = oAuthService.findUserMainToken("perococco", Platform.TWITCH).get();


        final var information = List.of(
                "broadcaster_id '" + token.getViewerIdentity().getViewerId() + "'",
                "client_id      '" + client.getClientId() + "'",
                "accesstoken    '" + token.getAccessToken() + "'");

        Files.write(Path.of("/home/perococco/token_info.txt"), information);

        /**
         * curl -X POST 'https://api.twitch.tv/helix/channel_points/custom_rewards?broadcaster_id=274637212' \
         * -H 'client-id: gx2pv4208cff0ig9ou7nk3riccffxt' \
         * -H 'Authorization: Bearer vjxv3i0l4zxru966wsnwji51tmpkj2' \
         * -H 'Content-Type: application/json' \
         * -d '{
         *   "title":"game analysis 1v1",
         *   "cost":50000
         * }'
         */
    }


    private void joinChannel() {
        final var userLogin = "perococco";
        final var chatUser = "perobobbot";
        final var bot = botService.findBotByName(userLogin, "Perobobbot").get();

        final var token = oAuthService.findUserToken(userLogin, Platform.TWITCH)
                                      .stream()
                                      .filter(d -> d.getViewerLogin().equals(chatUser))
                                      .findAny()
                                      .get();

        final var connectionInfo = new ChatConnectionInfo(
                bot.getId(),
                token.getViewerIdentity().getId(),
                Platform.TWITCH,
                bot.getName(),
                token.getViewerLogin(),
                token.getToken().getAccessToken()
        );

        io.getPlatform(Platform.TWITCH)
          .connect(connectionInfo)
          .thenCompose(c -> c.join("perococco"))
          .whenComplete((r, e) -> {
              if (e != null) {
                  System.err.println("Fail to join channel of perococco");
                  e.printStackTrace();
              } else {
                  r.send("Hello here !");
              }
          });
    }

    private void createClient() throws IOException {
        final var existing = clientService.findClientForPlatform(Platform.TWITCH).orElse(null);
        if (existing == null) {
            final var clientId = "m01e1fb0emhtr0toc6eydvl9zkuecu";
            final var clientSecret = Secret.with(Files.readAllLines(Path.of("/home/perococco/twitch_keys/perobobbot_app_secret.txt")).get(0));
            clientService.createClient(new CreateClientParameter(
                    Platform.TWITCH,
                    clientId,
                    clientSecret
            ));
        }
    }
}
