package bot.twitch.chat;

import bot.chat.core.Chat;
import bot.chat.core.ChatListener;
import bot.chat.websocket.WebSocketChatClient;
import lombok.NonNull;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author perococco
 **/
public class TestChat {

    public static final URI TWITCH_SSL_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");

    public static void main(String[] args) throws Exception {

        bot.chat.websocket.WebSocketChatClient c = new WebSocketChatClient(TWITCH_SSL_URI);
        final Chat chat = c.connect();

        chat.addChatListener(new ChatListener() {
            @Override
            public void onReceivedMessage(@NonNull String receivedMessage) {
                System.out.println(receivedMessage);
//                try {
//                    Files.writeString(Path.of("/home/perococco/chat.txt"), receivedMessage+"\n", StandardOpenOption.APPEND,
//                                      StandardOpenOption.CREATE);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onPostMessage(@NonNull String postMessage) {
                System.out.println("TestChat.onPostMessage(postMessage = [" + postMessage + "])");
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                throwable.printStackTrace();
            }
        });
        chat.postMessage("PASS oauth:q" + Files.readString(Path.of("/home/perococco/oauth.txt")).substring(1));
        Thread.sleep(1000);
        chat.postMessage("NICK perococco");
//        chat.postMessage("CAP REQ :twitch.tv/tags twitch.tv/commands twitch.tv/membership");
//        chat.postMessage("JOIN #joueur_du_grenier");
    }
}
