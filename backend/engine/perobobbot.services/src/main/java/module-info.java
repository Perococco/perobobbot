module perobobbot.services {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires perobobbot.common.lang;

    exports perobobbot.services;
    exports perococco.perobobbot.services to perobobbot.program.core;
}