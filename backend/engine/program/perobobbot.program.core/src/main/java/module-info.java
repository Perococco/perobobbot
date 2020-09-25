module perobobbot.program.core {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;

    requires com.google.common;
    requires org.apache.logging.log4j;
    requires perobobbot.service.core;


    exports perobobbot.program.core;

}