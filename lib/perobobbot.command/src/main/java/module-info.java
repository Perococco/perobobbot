module perobobbot.command {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.access;

    requires perobobbot.chat.core;

    requires org.apache.logging.log4j;

    exports perobobbot.command;
}
