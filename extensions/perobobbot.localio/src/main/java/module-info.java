import perobobbot.plugin.Plugin;
import perobobbot.localio.spring.LocalIOPlugin;

module perobobbot.consoleio {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;

    requires perobobbot.lang;
    requires perobobbot.chat.core;
    requires perobobbot.data.service;
    requires perobobbot.command;
    requires perobobbot.access;

    requires com.google.common;
    requires perobobbot.plugin;

    provides Plugin with LocalIOPlugin;
}
