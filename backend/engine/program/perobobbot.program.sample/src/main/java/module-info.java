module perobobbot.program.sample {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.program.core;
    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;

    requires com.google.common;

    exports perobobbot.program.sample;
}