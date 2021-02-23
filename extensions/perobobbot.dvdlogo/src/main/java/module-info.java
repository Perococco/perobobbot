import perobobbot.dvdlogo.DVDLogoExtensionFactory;
import perobobbot.plugin.Plugin;

module perobobbot.dvdlogo {
    requires static lombok;

    requires perobobbot.chat.core;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires perobobbot.command;
    requires perobobbot.plugin;
    requires perobobbot.access;

    requires java.desktop;
    requires com.google.common;

    exports perobobbot.dvdlogo;

    provides Plugin with DVDLogoExtensionFactory;

}
