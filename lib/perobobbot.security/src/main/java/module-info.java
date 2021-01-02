module perobobbot.security {
    requires static lombok;
    requires java.desktop;
    requires spring.security.core;
    requires com.google.common;

    requires perobobbot.lang;

    exports perobobbot.security;
}
