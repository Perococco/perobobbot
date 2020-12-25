package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.lang.Bot;
import perobobbot.lang.Credentials;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.extension.ExtensionManagerFactory;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class LaunchAtStartup implements ApplicationRunner {

    @Value("${perobot.io.twitch.channel}")
    private final String channelName;

    @Value("${perobot.io.twitch.nick}")
    private final String nick;

    @Value("${perobot.io.twitch.keyfile}")
    private final String keyPath;

    private final @NonNull ExtensionManagerFactory extensionManagerFactory;

    private final @NonNull IO io;

    private @NonNull Secret readTwitchChatSecret() throws IOException {
        return new Secret(Files.readString(Path.of(keyPath)));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Launch initialization");
        final Bot bot = Bot.builder()
                           .name("Perobobbot")
                           .credential(Platform.TWITCH, new Credentials(nick, readTwitchChatSecret()))
                           .credential(Platform.LOCAL, new Credentials(nick, new Secret("")))
                           .build();


        io.getPlatform(Platform.TWITCH)
          .connect(bot)
          .thenCompose(c -> c.join(channelName))
          .thenCombine(
                  io.getPlatform(Platform.LOCAL)
                    .connect(bot)
                    .thenCompose(c -> c.join("console"))
                  , (twitch, local) -> "Perobobbot is ready"
          ).whenComplete((r,t) -> {
              if (t!=null) {
                  System.err.println("Start up failed");
                  t.printStackTrace();
              }
              else {
                  System.out.println(r);
              }
        });

        extensionManagerFactory.create(bot);
    }


}
