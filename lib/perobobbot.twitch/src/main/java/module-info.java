import perobbot.twitch.oauth.spring.TwitchOAuthConfiguration;
import perobobbot.lang.Packages;

module perobobbot.twitch {
    requires static lombok;
    requires java.desktop;
    requires spring.context;
    requires spring.web;
    requires spring.webflux;
    requires perobobbot.http;
    requires perobobbot.lang;
    requires perobobbot.oauth;
    requires java.servlet;
    requires com.google.common;
    requires org.apache.logging.log4j;
    requires com.fasterxml.jackson.annotation;
    requires reactor.core;


    opens perobbot.twitch.oauth.spring to spring.context, spring.beans, spring.core;

    opens perobbot.twitch.oauth to com.fasterxml.jackson.databind;

    provides Packages with TwitchOAuthConfiguration;

}
