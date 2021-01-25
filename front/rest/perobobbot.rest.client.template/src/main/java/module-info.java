module perobobbot.rest.client.template {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.rest.com;
    requires perobobbot.rest.client.api;
    requires spring.web;
    requires com.google.common;


    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.guava;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires spring.core;

    exports perobobbot.rest.client.template;
}
