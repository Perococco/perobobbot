import perobobbot.play.PlayExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.play {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires perobobbot.access;
    requires perobobbot.command;

    requires org.apache.logging.log4j;
    requires com.google.common;
    requires perobobbot.chat.core;

    exports perobobbot.play;
    provides Plugin with PlayExtensionFactory;

}
