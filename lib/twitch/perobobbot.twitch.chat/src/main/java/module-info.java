/**
 * @author perococco
 **/
module perobobbot.twitch.chat {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.chat.core;
    requires perobobbot.chat.advanced;
    requires transitive perobobbot.irc;

    requires io.github.bucket4j.core;
    requires org.apache.logging.log4j;

    requires com.google.common;

    exports perobobbot.twitch.chat;
    exports perobobbot.twitch.chat.event;
    exports perobobbot.twitch.chat.message;
    exports perobobbot.twitch.chat.message.to;
    exports perobobbot.twitch.chat.message.from;

}
