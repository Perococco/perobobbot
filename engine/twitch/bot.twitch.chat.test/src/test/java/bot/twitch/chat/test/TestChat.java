package bot.twitch.chat.test;

import bot.common.lang.Secret;
import bot.twitch.chat.*;
import bot.twitch.chat.message.from.MessageFromTwitch;
import com.google.common.collect.ImmutableList;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author perococco
 **/
public class TestChat {

    public static void main(String[] args) throws Exception {

        final TwitchChatOptions options = TwitchChatOptions.builder()
                .nick("perococco")
                .secret(new Secret(Files.readString(Path.of("/home/perococco/oauth.txt"))))
                .channel(Channel.create("perococco"))
                .build();

        final TwitchChat chat = TwitchChat.create(options);

        chat.addTwitchChatListener(System.out::println);

        chat.start();
    }

    private static void displayMessages(ImmutableList<MessageFromTwitch> messages) {
        //messages.forEach(System.out::println);
    }
}
