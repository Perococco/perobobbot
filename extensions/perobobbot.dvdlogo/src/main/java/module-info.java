import perobobbot.dvdlogo.spring.DVDLogoExtensionFactory;
import perobobbot.lang.Plugin;

module perobobbot.dvdlogo {
    requires static lombok;

    requires perobobbot.chat.core;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires perobobbot.command;
    requires perobobbot.access;

    requires java.desktop;
    requires com.google.common;
    requires spring.context;

    exports perobobbot.dvdlogo;

    provides Plugin with DVDLogoExtensionFactory;

    opens perobobbot.dvdlogo.spring to spring.core,spring.beans,spring.context;
}
