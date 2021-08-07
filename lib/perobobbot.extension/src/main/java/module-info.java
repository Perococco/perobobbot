module perobobbot.extension {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.command;
    requires perobobbot.access;
    requires perobobbot.chat.core;
    requires perobobbot.messaging;
    requires perobobbot.plugin;
    requires transitive jplugman.api;

    requires org.apache.logging.log4j;


    exports perobobbot.extension;
}
