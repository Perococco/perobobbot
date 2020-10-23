module perobobbot.service.core {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires perobobbot.common.lang;

    exports perobobbot.service.core;
    exports perococco.perobobbot.service.core to perobobbot.program.core;
}