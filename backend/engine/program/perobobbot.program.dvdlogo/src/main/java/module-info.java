import perobobbot.program.core.ProgramFactory;
import perobobbot.program.dvdlogo.DVDLogoProgramFactory;

module perobobbot.program.dvdlogo {
    requires static lombok;

    requires perobobbot.program.core;
    requires perobobbot.overlay;
    requires java.desktop;
    requires perobobbot.access.core;
    requires perobobbot.chat.core;
    requires perobobbot.service.core;
    requires com.google.common;

    exports perobobbot.program.dvdlogo;

    provides ProgramFactory with DVDLogoProgramFactory;
}