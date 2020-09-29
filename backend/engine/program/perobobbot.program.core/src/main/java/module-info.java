import perobobbot.program.core.ProgramFactory;

module perobobbot.program.core {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;
    requires perobobbot.service.core;

    requires com.google.common;
    requires org.apache.logging.log4j;

    exports perobobbot.program.core;

    uses ProgramFactory;

}