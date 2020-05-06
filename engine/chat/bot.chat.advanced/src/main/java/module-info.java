import bot.chat.advanced.AdvancedChatFactory;
import perococco.bot.chat.advanced.PerococcoAdvancedChatFactory;

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

    uses AdvancedChatFactory;
    provides AdvancedChatFactory with PerococcoAdvancedChatFactory;
}
