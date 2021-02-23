import perobobbot.pause.spring.PauseExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.pause {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.plugin;
    requires perobobbot.access;
    requires perobobbot.command;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires perobobbot.chat.core;
    requires com.google.common;

    exports perobobbot.pause;
    provides Plugin with PauseExtensionFactory;
}
