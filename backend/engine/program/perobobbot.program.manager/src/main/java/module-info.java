module perobobbot.program.manager {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires com.google.common;

    requires perobobbot.program.core;
    requires perobobbot.common.messaging;
    requires perobobbot.access.core;
    requires perobobbot.service.core;
    requires perobobbot.common.lang;

    exports perobobbot.program.manager;

    uses perobobbot.program.core.ProgramFactory;

}