import perobobbot.twitch.oauth.impl.spring.TwitchOAuthConfiguration;
import perobobbot.lang.Packages;

module perobobbot.twitch.oauth.impl {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.http;
    requires perobobbot.lang;
    requires perobobbot.oauth;
    requires perobobbot.data.service;
    requires transitive perobobbot.twitch.oauth.api;


    requires spring.context;
    requires spring.web;
    requires spring.webflux;
    requires java.servlet;
    requires com.google.common;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.guava;
    requires com.fasterxml.jackson.datatype.jdk8;

    requires reactor.core;


    opens perobobbot.twitch.oauth.impl.spring to spring.context, spring.beans, spring.core;
    opens perobobbot.twitch.oauth.impl to com.fasterxml.jackson.databind;

    provides Packages with TwitchOAuthConfiguration;

}
