package perobobbot.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.data.com.UnknownBot;
import perobobbot.data.service.BotService;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.ExtensionService;
import perobobbot.lang.Bot;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@Log4j2
@RequiredArgsConstructor
public class PerococcoRunner implements ApplicationRunner {

    @EventService
    private final @NonNull BotService botService;
    @EventService
    private final @NonNull CredentialService credentialService;
    @EventService
    private final @NonNull ExtensionService extensionService;

    private final @NonNull IO io;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final Bot bot = getOrCreateBot("perococco");

        io.getPlatform(Platform.TWITCH)
          .connect(bot)
          .thenCompose(c -> c.join("perococco"))
          .whenComplete((messageChannelIO,error) -> {
              if (messageChannelIO != null) {
                  whenJoined(messageChannelIO);
              } else {
                  LOG.error("Could not connect to perococco chat",error);
              }
          });
    }

    private void whenJoined(@NonNull MessageChannelIO messageChannelIO) {
        //messageChannelIO.send("Ready!!");
    }

    private Bot getOrCreateBot(String login) throws IOException {
        final var existing = botService.findBotByName(login,"perobobbot");
        if (existing.isPresent()) {
            return existing.get();
        }

        final var bot = botService.createBot(login,"perobobbot");
        final var cred = credentialService.createCredential(login, Platform.TWITCH);
        credentialService.updateCredential(cred.getId(),new Credential("perobobbot", readSecret()));

        botService.attachCredential(bot.getId(),cred.getId());

        enableAllExtensions(bot);

        return botService.findBot(bot.getId()).orElseThrow(() -> new UnknownBot(bot.getId()));
    }

    private void enableAllExtensions(Bot bot) {
        var extensions = extensionService.listAllExtensions();
        extensions.forEach(e -> botService.enableExtension(bot.getId(),e.getName()));
    }

    private Secret readSecret() throws IOException {
        return Secret.of(Files.readString(Path.of("/home/perococco/twitch_keys/perobobbot_user.txt")));
    }
}
