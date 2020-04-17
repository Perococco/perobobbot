import bot.chat.advanced.AdvancedChatClientFactory;
import perococco.bot.chat.advanced.PerococcoAdvancedChatClientFactory;

/**
 * @author perococco
 **/
module bot.chat.advanced {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.google.common;
    requires bot.chat.core;
    requires transitive bot.common.lang;

    exports bot.chat.advanced;
    uses AdvancedChatClientFactory;

    provides AdvancedChatClientFactory with PerococcoAdvancedChatClientFactory;
}
