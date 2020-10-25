import perobobbot.program.core.ProgramFactory;
import perobobbot.program.greeter.GreeterProgramFactory;

module perobobbot.program.greeter {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires org.apache.logging.log4j;

    requires perobobbot.common.lang;
    requires perobobbot.program.core;
    requires perobobbot.access.core;
    requires perobobbot.service.core;
    requires perobobbot.chat.core;


    exports perobobbot.program.greeter;

    provides ProgramFactory with GreeterProgramFactory;
}