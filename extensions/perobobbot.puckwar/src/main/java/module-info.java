import perobobbot.plugin.Plugin;
import perobobbot.puckwar.PuckWarExtensionFactory;

module perobobbot.ext.puckwar {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.overlay.api;
    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.extension;
    requires perobobbot.command;
    requires perobobbot.access;
    requires perobobbot.physics;


    requires org.apache.logging.log4j;
    requires com.google.common;


    provides Plugin with PuckWarExtensionFactory;

}
