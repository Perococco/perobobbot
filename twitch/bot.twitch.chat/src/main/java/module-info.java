/**
 * @author perococco
 **/
module bot.twitch.chat {
    requires static lombok;
    requires java.desktop;

    requires bot.chat.core;
    requires bot.chat.websocket;
    requires bot.chat.advanced;
    requires bot.common.irc;

    requires org.apache.logging.log4j;

    requires com.google.common;

    exports bot.twitch.chat;
    exports bot.twitch.chat.message;
    exports bot.twitch.chat.message.to;
    exports bot.twitch.chat.message.from;

}
