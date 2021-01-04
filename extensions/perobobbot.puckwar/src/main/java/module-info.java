import perobobbot.lang.Plugin;
import perobobbot.puckwar.spring.PuckWarExtensionFactory;

module perobobbot.ext.puckwar {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.overlay.api;
    requires perobobbot.lang;
    requires perobobbot.extension;
    requires perobobbot.command;
    requires perobobbot.access;
    requires perobobbot.physics;


    requires org.apache.logging.log4j;
    requires com.google.common;

    requires spring.context;


    provides Plugin with PuckWarExtensionFactory;

    opens perobobbot.puckwar.spring to spring.core, spring.beans, spring.context;
}
