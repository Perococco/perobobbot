import bot.chat.advanced.AdvancedChatManagerFactory;
import perococco.bot.chat.advanced.PerococcoAdvancedChatManagerFactory;

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
    exports bot.chat.advanced.event;

    uses AdvancedChatManagerFactory;
    provides AdvancedChatManagerFactory with PerococcoAdvancedChatManagerFactory;
}
