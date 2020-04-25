/**
 * @author perococco
 **/
module bot.twitch.chat {
    requires static lombok;
    requires java.desktop;

    requires bot.chat.core;
    requires bot.chat.websocket;
    requires bot.chat.advanced;
    requires transitive bot.common.irc;

    requires io.github.bucket4j.core;

    requires org.apache.logging.log4j;

    requires com.google.common;

    exports bot.twitch.chat;
    exports bot.twitch.chat.event;
    exports bot.twitch.chat.message;
    exports bot.twitch.chat.message.to;
    exports bot.twitch.chat.message.from;

    exports perococco.bot.twitch.chat to bot.twitch.chat.test;
    exports perococco.bot.twitch.chat.state to bot.twitch.chat.test;
    exports perococco.bot.twitch.chat.actions to bot.twitch.chat.test;
}
