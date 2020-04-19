import bot.chat.core.ChatManagerFactory;
import bot.chat.websocket.WebSocketChatManagerFactory;

import javax.websocket.WebSocketContainer;

/**
 * @author perococco
 **/
module bot.chat.websocket {
    requires static lombok;
    requires java.desktop;

    requires transitive bot.chat.core;

    requires javax.websocket.client.api;

    requires org.apache.logging.log4j;

    exports bot.chat.websocket;
    provides ChatManagerFactory with WebSocketChatManagerFactory;
}
