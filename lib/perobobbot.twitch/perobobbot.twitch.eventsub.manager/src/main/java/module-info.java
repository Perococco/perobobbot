import perobobbot.lang.Packages;
import perobobbot.twitch.eventsub.manager.spring.TwitchEventSubConfiguration;

module perobobbot.twitch.event.sub.manager {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.http;
    requires perobobbot.twitch.client.api;
    requires perobobbot.data.service;
    requires perobobbot.eventsub;
    requires transitive perobobbot.twitch.event.sub.api;

    requires java.servlet;
    requires reactor.core;
    requires org.reactivestreams;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    requires com.google.common;
    requires org.apache.logging.log4j;
    requires spring.core;
    requires spring.context;
    requires spring.beans;


    provides Packages with TwitchEventSubConfiguration;

    opens perobobbot.twitch.eventsub.manager._private to com.fasterxml.jackson.databind, spring.beans, spring.context, spring.core, spring.messaging;
    opens perobobbot.twitch.eventsub.manager.spring to com.fasterxml.jackson.databind, spring.beans, spring.context, spring.core, spring.messaging;
    opens perobobbot.twitch.eventsub.manager.spring.handler to com.fasterxml.jackson.databind, spring.beans, spring.context, spring.core, spring.messaging;

}
