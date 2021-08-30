module perobobbot.security.core {
    requires static lombok;
    requires java.desktop;
    requires spring.security.core;
    requires spring.security.web;
    requires com.google.common;

    requires org.apache.logging.log4j;

    requires transitive perobobbot.lang;
    requires transitive perobobbot.security.com;

    requires jjwt;

    opens perobobbot.security.core to spring.core;

    exports perobobbot.security.core;
    exports perobobbot.security.core.jwt;
}
