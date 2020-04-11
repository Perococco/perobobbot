import bot.chat.core.ReconnectingChatClientFactory;
import perococco.bot.chat.core.PerococcoReconnectingChatClient;

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
    uses ReconnectingChatClientFactory;
    provides ReconnectingChatClientFactory with PerococcoReconnectingChatClient;
}
