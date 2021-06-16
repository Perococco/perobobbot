import perobobbot.lang.JsonModuleProvider;
import perobobbot.twitch.eventsub.api.deser.EventSubModule;

module perobobbot.twitch.event.sub.api {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;

    requires reactor.core;

    requires com.google.common;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.guava;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires org.apache.logging.log4j;
    requires java.servlet;


    opens perobobbot.twitch.eventsub.api to com.fasterxml.jackson.databind;
    opens perobobbot.twitch.eventsub.api.event to com.fasterxml.jackson.databind;

    provides JsonModuleProvider with EventSubModule;

    exports perobobbot.twitch.eventsub.api;
    exports perobobbot.twitch.eventsub.api.event;
    exports perobobbot.twitch.eventsub.api.subscription;
    exports perobobbot.twitch.eventsub.api.deser;



}
