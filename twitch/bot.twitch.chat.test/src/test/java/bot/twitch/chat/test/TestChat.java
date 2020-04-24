package bot.twitch.chat.test;

import bot.twitch.chat.TwitchChatManager;
import bot.twitch.chat.TwitchChatOAuth;
import bot.twitch.chat.TwitchReceiptSlip;
import bot.twitch.chat.message.from.MessageFromTwitch;
import com.google.common.collect.ImmutableList;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author perococco
 **/
public class TestChat {

    public static void main(String[] args) throws Exception {

        final TwitchChatManager chat = TwitchChatManager.create();

        chat.addTwitchChatListener(
                event -> event.acceptIfIsReceiveMessage(m -> displayMessages(m.messages()))
        );

        final TwitchChatOAuth oAuth = TwitchChatOAuth.create("perococco",Files.readString(Path.of("/home/perococco/oauth.txt")));

        chat.start(oAuth)
            .thenCompose(r -> chat.join("gom4rt"))
            .thenApply(TwitchReceiptSlip::slipAnswer)
            .thenAccept(System.out::println)
            .toCompletableFuture().get();



    }

    private static void displayMessages(ImmutableList<MessageFromTwitch> messages) {
        //messages.forEach(System.out::println);
    }
}
