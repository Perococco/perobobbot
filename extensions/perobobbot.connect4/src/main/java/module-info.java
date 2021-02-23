import perobobbot.connect4.Connect4ExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.ext.connect4 {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires perobobbot.overlay.api;
    requires perobobbot.extension;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.physics;
    requires perobobbot.poll;

    requires org.apache.logging.log4j;


    provides Plugin with Connect4ExtensionFactory;

}
