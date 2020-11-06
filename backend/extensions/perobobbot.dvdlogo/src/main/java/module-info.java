import perobobbot.dvdlogo.DVDLogoProgramFactory;
import perobobbot.program.core.ProgramFactory;

module perobobbot.dvdlogo {
    requires static lombok;

    requires perobobbot.services;
    requires perobobbot.access;
    requires perobobbot.program.core;
    requires perobobbot.chat.core;
    requires perobobbot.overlay;

    requires java.desktop;
    requires com.google.common;
    requires perobobbot.common.messaging;

    exports perobobbot.dvdlogo;

    provides ProgramFactory with DVDLogoProgramFactory;
}