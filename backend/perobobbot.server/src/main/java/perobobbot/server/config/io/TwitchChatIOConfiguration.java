package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.common.lang.Secret;
import perobobbot.server.config.MessageGateway;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatIO;
import perobobbot.twitch.chat.TwitchChatOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@RequiredArgsConstructor
public class TwitchChatIOConfiguration {

    @Value("${perobot.io.twitch.channel}")
    private final String channelName;

    @Value("${perobot.io.twitch.nick}")
    private final String nick;

    @Value("${perobot.io.twitch.keyfile}")
    private final String keyPath;

    @NonNull
    private final MessageGateway messageGateway;

    @Bean
    public TwitchChatIO twitchChatIO() throws Exception {
        final TwitchChat twitchChat = createTwitchChat();
        final TwitchChatIO twitchChatIO = twitchChat.startAndWait();

        twitchChatIO.addPrivateMessageListener(message -> message.toMessage().ifPresent(messageGateway::sendMessage));

        return twitchChatIO;
    }

    private @NonNull TwitchChat createTwitchChat() throws IOException {
        final TwitchChatOptions options = createTwitchChatOptions();
        return TwitchChat.create(options);
    }

    private @NonNull TwitchChatOptions createTwitchChatOptions() throws IOException {
        final Channel channel = Channel.create(this.channelName);
        final Secret secret = readTwitchChatSecret();

        return TwitchChatOptions.builder()
                                .channel(channel)
                                .nick(nick)
                                .secret(secret)
                                .build();
    }

    private @NonNull Secret readTwitchChatSecret() throws IOException {
        return new Secret(Files.readString(Path.of(keyPath)));
    }


}
