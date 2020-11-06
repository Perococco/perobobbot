import perobobbot.program.core.ProgramFactory;
import perobobbot.greeter.GreeterFactory;

module perobobbot.greeter {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires org.apache.logging.log4j;

    requires perobobbot.common.lang;
    requires perobobbot.program.core;
    requires perobobbot.access;
    requires perobobbot.services;
    requires perobobbot.chat.core;
    requires perobobbot.common.messaging;


    exports perobobbot.greeter;

    provides ProgramFactory with GreeterFactory;
}