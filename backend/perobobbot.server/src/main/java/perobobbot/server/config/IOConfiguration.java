package perobobbot.server.config;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.Platform;
import perobobbot.common.lang.Secret;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchChat;
import perobobbot.twitch.chat.TwitchChatIO;
import perobobbot.twitch.chat.TwitchChatOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

//TODO external configuration for Twitch Chat
@Configuration
@Log4j2
public class IOConfiguration {

    @Bean
    @Service
    public IO io(@NonNull TwitchChatIO twitchChatIO) {
        return IO.builder()
                 .add(Platform.TWITCH, twitchChatIO)
                 .build();
    }

    @Bean
    public TwitchChatIO createTwitchIO(@NonNull ChatGateway chatGateway) throws Exception {
        final Channel perococco = Channel.create("perococco");
        final TwitchChatOptions options = createTwitchChatOptions(perococco);
        final TwitchChat twitchChat = TwitchChat.create(options);

        final TwitchChatIO twitchChatIO = twitchChat.start()
                                                    .toCompletableFuture()
                                                    .get();
        twitchChatIO.addPrivateMessageListener(message -> message.toMessage().ifPresent(chatGateway::sendMessage));

        return twitchChatIO;
    }

    @NonNull
    private TwitchChatOptions createTwitchChatOptions(@NonNull Channel channel) throws IOException {
        final Secret secret = new Secret(Files.readString(Path.of("/home/perococco/twitch_keys/perobobbot.txt")));
        return TwitchChatOptions.builder()
                                .channel(channel)
                                .nick("perobobbot")
                                .secret(secret)
                                .build();
    }

}
