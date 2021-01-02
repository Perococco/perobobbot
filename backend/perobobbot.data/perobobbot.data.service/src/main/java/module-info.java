module perobobbot.data.service {
    requires static lombok;
    requires java.desktop;
    requires transitive perobobbot.data.com;

    requires com.google.common;

    exports perobobbot.data.service;
}
