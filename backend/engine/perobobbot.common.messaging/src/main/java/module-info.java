module perobobbot.common.messaging {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.common.command;

    exports perobobbot.common.messaging;
}