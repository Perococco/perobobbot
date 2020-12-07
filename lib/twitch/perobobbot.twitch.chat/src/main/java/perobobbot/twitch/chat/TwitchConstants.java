package perobobbot.twitch.chat;

import java.net.URI;

public interface TwitchConstants {

    URI TWITCH_CHAT_URI = URI.create("wss://irc-ws.chat.twitch.tv:443");
    String CHAT_LOGIN_AUTHENTICATION_FAILED = "Login authentication failed";
    String CHAT_IMPROPERLY_FORMATTED_AUTH = "Improperly formatted auth";
}
