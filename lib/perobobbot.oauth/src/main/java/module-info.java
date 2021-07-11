import perobobbot.lang.JsonModuleProvider;
import perobobbot.oauth.OAuthModule;

module perobobbot.oauth {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;


    requires com.google.common;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.guava;
    requires spring.web;
    requires spring.webflux;
    requires spring.context;
    requires perobobbot.http;
    requires java.servlet;
    requires org.apache.logging.log4j;
    requires reactor.core;

    opens perobobbot.oauth to com.fasterxml.jackson.databind;

    exports perobobbot.oauth;

    provides JsonModuleProvider with OAuthModule;
}
