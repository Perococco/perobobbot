package bot.launcher;

import bot.common.lang.Secret;
import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChat;
import bot.twitch.chat.TwitchChatIO;
import bot.twitch.chat.TwitchChatOptions;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ChatBotLauncher implements ApplicationRunner {


    private void launchBot(@NonNull TwitchChatIO chat) {
        final PingBot pingBot = new PingBot(chat);
        pingBot.start();

    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        final Channel perococco = Channel.create("perococco");
        final TwitchChatOptions options = createTwitchChatOptions(perococco);
        final TwitchChat twitchChat = TwitchChat.create(options);

        twitchChat.start()
        .thenAccept(this::launchBot);

    }


    private TwitchChatOptions createTwitchChatOptions(@NonNull Channel channel) throws IOException {
        final Secret secret = new Secret(Files.readString(Path.of("/home/perococco/twitch_keys/perobobbot.txt")));
        return TwitchChatOptions.builder()
                                .channel(channel)
                                .nick("perobobbot")
                                .secret(secret)
                                .build();
    }

}
