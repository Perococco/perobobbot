module perobobbot.messaging {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.command;

    exports perobobbot.messaging;
}