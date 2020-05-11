import perobobbot.chat.core.ChatFactory;
import perobobbot.chat.websocket.WebSocketChatFactory;

/**
 * @author perococco
 **/
module perobobbot.chat.websocket {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.chat.core;

    requires javax.websocket.client.api;

    requires org.apache.logging.log4j;

    exports perobobbot.chat.websocket;
    provides ChatFactory with WebSocketChatFactory;
}
