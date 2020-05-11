module perobobbot.program.core {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;
    requires org.apache.logging.log4j;


    exports perobobbot.program.core;
}