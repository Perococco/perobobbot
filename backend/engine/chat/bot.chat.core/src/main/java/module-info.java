import bot.chat.core.ChatFactory;

/**
 * @author perococco
 **/
module bot.chat.core {
    requires static lombok;
    requires java.desktop;

    requires transitive bot.common.lang;

    requires com.google.common;
    requires org.apache.logging.log4j;

    exports bot.chat.core;
    exports bot.chat.core.event;
    uses ChatFactory;
}
