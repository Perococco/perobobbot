import perobobbot.chat.core.ChatFactory;

/**
 * @author perococco
 **/
module perobobbot.chat.core {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.lang;

    requires com.google.common;
    requires org.apache.logging.log4j;

    exports perobobbot.chat.core;
    exports perobobbot.chat.core.event;

    uses ChatFactory;
}
