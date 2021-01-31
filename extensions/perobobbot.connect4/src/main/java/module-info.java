import perobobbot.connect4.spring.Connect4ExtensionFactory;
import perobobbot.lang.Plugin;

module perobobbot.ext.connect4 {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires perobobbot.overlay.api;
    requires perobobbot.extension;
    requires spring.context;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.lang;
    requires perobobbot.physics;
    requires perobobbot.poll;

    requires org.apache.logging.log4j;


    provides Plugin with Connect4ExtensionFactory;

    opens perobobbot.connect4.spring to spring.core, spring.beans, spring.context;

}
