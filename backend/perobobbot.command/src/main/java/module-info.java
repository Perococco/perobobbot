module perobobbot.command {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.access;


    exports perobobbot.command;
}