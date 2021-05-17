module perobobbot.oauth {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.plugin;

    requires com.google.common;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.guava;
    requires spring.web;
    requires spring.context;
    requires perobobbot.http;
    requires java.servlet;

    opens perobobbot.oauth to com.fasterxml.jackson.databind;

    exports perobobbot.oauth;
}
