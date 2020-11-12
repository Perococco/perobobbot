import perobobbot.dvdlogo.spring.DVDLogoConfiguration;
import perobobbot.lang.Packages;

module perobobbot.dvdlogo {
    requires static lombok;

    requires perobobbot.chat.core;
    requires perobobbot.extension;
    requires perobobbot.overlay.api;
    requires perobobbot.common.command;
    requires perobobbot.access;

    requires java.desktop;
    requires com.google.common;
    requires spring.context;

    exports perobobbot.dvdlogo;

    provides Packages with DVDLogoConfiguration;

    opens perobobbot.dvdlogo.spring to spring.core,spring.beans,spring.context;
}