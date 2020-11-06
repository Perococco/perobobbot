import perobobbot.program.core.ProgramFactory;

module perobobbot.program.core {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;
    requires perobobbot.services;
    requires perobobbot.chat.core;

    requires com.google.common;
    requires org.apache.logging.log4j;
    requires perobobbot.access;
    requires perobobbot.common.messaging;

    exports perobobbot.program.core;

    uses ProgramFactory;

}