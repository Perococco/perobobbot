module perobobbot.service.core {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires perobobbot.common.lang;

    exports perobobbot.service.core;
}