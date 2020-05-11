import perobobbot.chat.advanced.AdvancedChatFactory;
import perococco.perobobbot.chat.advanced.PerococcoAdvancedChatFactory;

/**
 * @author perococco
 **/
module perobobbot.chat.advanced {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.google.common;
    requires perobobbot.chat.core;
    requires transitive perobobbot.common.lang;

    exports perobobbot.chat.advanced;
    exports perobobbot.chat.advanced.event;

    uses AdvancedChatFactory;
    provides AdvancedChatFactory with PerococcoAdvancedChatFactory;
}
