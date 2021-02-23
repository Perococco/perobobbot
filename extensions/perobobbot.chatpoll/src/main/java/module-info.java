import perobobbot.chatpoll.ChatPollExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.ext.chatpoll {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;

    requires com.google.common;


    requires perobobbot.poll;
    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.chat.core;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires spring.context;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.messaging;

    exports perobobbot.chatpoll;

    provides Plugin with ChatPollExtensionFactory;

}
