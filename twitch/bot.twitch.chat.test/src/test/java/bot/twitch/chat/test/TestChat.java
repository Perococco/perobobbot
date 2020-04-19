package bot.twitch.chat.test;

import bot.common.lang.Nil;
import bot.twitch.chat.TwitchChatListener;
import bot.twitch.chat.TwitchChatManager;
import bot.twitch.chat.TwitchChatOAuth;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.MessageToTwitch;
import lombok.NonNull;
import lombok.Synchronized;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public class TestChat {

    public static final URI TWITCH_SSL_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");

    public static void main(String[] args) throws Exception {

        final TwitchChatManager chat = TwitchChatManager.create();


        chat.addTwitchChatListener(
                new TwitchChatListener() {

                    @Override
                    public void onMessageFromTwitch(@NonNull MessageFromTwitch messageFromTwitch) {
                        System.out.println(messageFromTwitch);
                    }

                    @Override
                    public void onMessageToTwitch(@NonNull MessageToTwitch messageToTwitch) {

                    }
                }
        );

        final TwitchChatOAuth oAuth = TwitchChatOAuth.create("perococco",Files.readString(Path.of("/home/perococco/oauth.txt")));

        chat.start(oAuth)
            .thenCompose(r -> r.join("perococco"))
            .thenCompose(r -> r.part("perococco"))
            .whenComplete((r,t) -> chat.requestStop())
            .toCompletableFuture().get();

        System.exit(0);
    }
}
