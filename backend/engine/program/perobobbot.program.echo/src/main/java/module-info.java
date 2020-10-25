import perobobbot.program.echo.EchoProgramFactory;
import perobobbot.program.core.ProgramFactory;

module perobobbot.program.echo {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.program.core;
    requires perobobbot.chat.core;
    requires perobobbot.service.core;
    requires perobobbot.access.core;
    requires com.google.common;

    exports perobobbot.program.echo;

    provides ProgramFactory with EchoProgramFactory;
}