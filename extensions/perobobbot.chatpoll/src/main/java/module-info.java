import perobobbot.chatpoll.spring.ChatPollExtensionFactory;
import perobobbot.lang.Plugin;

module perobobbot.ext.chatpoll {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;

    requires com.google.common;


    requires perobobbot.poll;
    requires perobobbot.lang;
    requires perobobbot.chat.core;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires spring.context;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.messaging;

    exports perobobbot.chatpoll;

    provides Plugin with ChatPollExtensionFactory;

    opens perobobbot.chatpoll.spring to spring.core, spring.beans,spring.context;

}
