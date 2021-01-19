module perobobbot.http {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;

    requires spring.web;
    requires java.servlet;
    requires com.google.common;

    exports perobobbot.http;
}
