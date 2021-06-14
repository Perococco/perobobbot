import perobobbot.lang.Packages;
import perobobbot.twutch.eventsub.manager.EventSubManagerConfiguration;

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


    opens perobobbot.twutch.eventsub.manager to com.fasterxml.jackson.databind, spring.core,spring.beans,spring.context;

    provides Packages with EventSubManagerConfiguration;

    exports perobobbot.twutch.eventsub.manager;
}
