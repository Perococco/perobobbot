import perobobbot.lang.Plugin;
import perobobbot.pause.spring.PauseExtensionFactory;

module perobobbot.pause {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires spring.context;
    requires perobobbot.chat.core;
    requires com.google.common;

    exports perobobbot.pause;
    provides Plugin with PauseExtensionFactory;

    opens perobobbot.pause.spring to spring.context,spring.beans,spring.core;
}
