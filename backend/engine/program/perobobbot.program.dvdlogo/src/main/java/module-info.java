import perobobbot.program.core.ProgramFactory;
import perobobbot.program.dvdlogo.DVDLogoProgramFactory;

module perobobbot.program.dvdlogo {
    requires static lombok;

    requires perobobbot.service.core;
    requires perobobbot.access.core;
    requires perobobbot.program.core;
    requires perobobbot.chat.core;
    requires perobobbot.overlay;

    requires java.desktop;
    requires com.google.common;
    requires perobobbot.common.messaging;

    exports perobobbot.program.dvdlogo;

    provides ProgramFactory with DVDLogoProgramFactory;
}