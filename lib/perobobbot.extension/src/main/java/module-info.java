module perobobbot.extension {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.command;
    requires perobobbot.overlay.api;
    requires perobobbot.access;
    requires perobobbot.chat.core;
    requires perobobbot.messaging;

    exports perobobbot.extension;
}
