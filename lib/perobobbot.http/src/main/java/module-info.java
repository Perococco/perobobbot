module perobobbot.http {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;

    requires org.apache.logging.log4j;
    requires spring.web;
    requires spring.webflux;
    requires java.servlet;
    requires com.google.common;
    requires reactor.core;

    exports perobobbot.http;
}
