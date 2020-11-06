import perobobbot.echo.EchoProgramFactory;
import perobobbot.program.core.ProgramFactory;

module perobobbot.echo {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.program.core;
    requires perobobbot.chat.core;
    requires perobobbot.services;
    requires perobobbot.access;
    requires com.google.common;
    requires perobobbot.common.messaging;

    exports perobobbot.echo;

    provides ProgramFactory with EchoProgramFactory;
}