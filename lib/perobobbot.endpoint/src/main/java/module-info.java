module perobobbot.endpoint {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.security.reactor;
    requires reactor.core;
    requires spring.webmvc;
    requires com.fasterxml.jackson.databind;
    requires spring.web;
    requires perobobbot.data.service;
    requires java.servlet;
    requires spring.security.core;
    requires com.google.common;
    requires spring.beans;

    exports perobobbot.endpoint;
}