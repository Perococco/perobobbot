module perobobbot.security.reactor {
    requires static lombok;
    requires java.desktop;
    requires spring.security.core;
    requires perobobbot.oauth;
    requires reactor.core;

    exports perobobbot.security.reactor;
}