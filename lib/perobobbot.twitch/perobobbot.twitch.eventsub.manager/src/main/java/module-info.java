import perobobbot.lang.Packages;
import perobobbot.twitch.eventsub.manager.EventSubManagerConfiguration;

module perobobbot.twitch.event.sub.manager {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.http;
    requires perobobbot.twitch.client.api;
    requires transitive perobobbot.twitch.event.sub.api;

    requires java.servlet;
    requires reactor.core;
    requires spring.web;
    requires com.fasterxml.jackson.databind;

    requires com.google.common;
    requires org.apache.logging.log4j;
    requires spring.core;
    requires spring.context;
    requires spring.beans;
    requires spring.integration.core;


    opens perobobbot.twitch.eventsub.manager to com.fasterxml.jackson.databind, spring.core,spring.beans,spring.context, spring.messaging;
    opens perobobbot.twitch.eventsub.manager._private to com.fasterxml.jackson.databind, spring.core,spring.beans,spring.context, spring.messaging;
    exports perobobbot.twitch.eventsub.manager;

    provides Packages with EventSubManagerConfiguration;
}
