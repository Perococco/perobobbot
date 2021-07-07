import perobobbot.lang.Packages;
import perobobbot.twitch.client.webclient.spring.TwitchApiConfiguration;

module perobobbot.twitch.client.webclient {
    requires static lombok;
    requires java.desktop;
    requires spring.webflux;
    requires perobobbot.lang;
    requires perobobbot.proxy;
    requires perobobbot.oauth;
    requires perobobbot.data.service;
    requires transitive perobobbot.twitch.client.api;
    requires spring.web;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires reactor.core;
    requires org.aspectj.weaver;
    requires perobobbot.oauth.tools;
    requires perobobbot.http;
    requires org.apache.logging.log4j;
    requires com.google.common;

    opens perobobbot.twitch.client.webclient.spring to spring.core, spring.context, spring.beans, spring.aop;
//    opens perobobbot.twitch.client.webclient to spring.core, spring.context, spring.beans, com.fasterxml.jackson.databind;
//    opens perobobbot.twitch.client.webclient.eventsub to com.fasterxml.jackson.databind, spring.beans, spring.context, spring.core;
//    opens perobobbot.twitch.client.webclient.games to com.fasterxml.jackson.databind, spring.beans, spring.context, spring.core;

    provides Packages with TwitchApiConfiguration;
}
