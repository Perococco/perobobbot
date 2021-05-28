open module perobobbot.server.test {
    requires static lombok;
    requires java.desktop;

    requires spring.test;
    requires org.junit.jupiter.api;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.orm;
    requires perobobbot.data.event;
    requires perobobbot.data.security;
    requires perobobbot.data.service;
    requires perobobbot.data.jpa;
}
