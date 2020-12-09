package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.chat.core.ChatAuthentication;
import perobobbot.extension.ExtensionManagerFactory;
import perobobbot.lang.Secret;
import perobobbot.twitch.chat.TwitchChatPlatform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class LaunchAtStartup implements ApplicationRunner, DisposableBean {

    @Value("${perobot.io.twitch.channel}")
    private final String channelName;

    @Value("${perobot.io.twitch.nick}")
    private final String nick;

    @Value("${perobot.io.twitch.keyfile}")
    private final String keyPath;

    private final @NonNull ExtensionManagerFactory extensionManagerFactory;

    private final @NonNull TwitchChatPlatform twitchChatPlatform;

    private @NonNull Secret readTwitchChatSecret() throws IOException {
        return new Secret(Files.readString(Path.of(keyPath)));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final ChatAuthentication authentication = new ChatAuthentication(nick,readTwitchChatSecret());
        twitchChatPlatform.connect(authentication)
                          .thenCompose(c -> c.join(channelName))
                          .thenAccept(this::withChatChannel);

    }

    private void withChatChannel(@NonNull MessageChannelIO messageChannelIo) {
        messageChannelIo.send("Connected !!!");
        extensionManagerFactory.create(nick);
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("SHOULD DISCONNECT");
    }
}
