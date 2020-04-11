/**
 * @author perococco
 **/
module bot.chat.websocket {
    requires static lombok;
    requires java.desktop;

    requires javax.websocket.client.api;

    requires transitive bot.chat.core;
    requires org.apache.logging.log4j;

    exports bot.chat.websocket;
}
