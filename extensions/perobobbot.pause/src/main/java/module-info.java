import perobobbot.lang.Packages;
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

    exports perobobbot.pause;
    provides Packages with PauseExtensionFactory;

    opens perobobbot.pause.spring to spring.context,spring.beans,spring.core;
}