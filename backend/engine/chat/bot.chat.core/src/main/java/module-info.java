import bot.chat.core.ChatFactory;

/**
 * @author perococco
 **/
module perobobbot.chat.core {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.common.lang;

    requires com.google.common;
    requires org.apache.logging.log4j;

    exports bot.chat.core;
    exports bot.chat.core.event;
    uses ChatFactory;
}
