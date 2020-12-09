package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.ChatAuthentication;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.extension.ExtensionManagerFactory;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
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
        final ChatAuthentication authentication = new ChatAuthentication(nick, readTwitchChatSecret());
        io.getPlatform(Platform.TWITCH)
          .connect(authentication)
          .thenCompose(c -> c.join(channelName))
          .thenAccept(this::withChatChannel);

        io.getPlatform(Platform.LOCAL)
          .connect(authentication)
          .thenCompose(c -> c.join("DUMMY"))
          .thenAccept(c -> c.send("Perobobbot is ready!"));

    }

    private void withChatChannel(@NonNull MessageChannelIO messageChannelIo) {
        extensionManagerFactory.create(nick);
    }

}
