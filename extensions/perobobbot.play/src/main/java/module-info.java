import perobobbot.lang.Plugin;
import perobobbot.play.spring.PlayExtensionFactory;

module perobobbot.play {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires perobobbot.access;
    requires perobobbot.command;

    requires org.apache.logging.log4j;
    requires spring.context;
    requires com.google.common;
    requires perobobbot.chat.core;

    exports perobobbot.play;
    provides Plugin with PlayExtensionFactory;



    opens perobobbot.play.spring to spring.context,spring.beans,spring.core;

}
