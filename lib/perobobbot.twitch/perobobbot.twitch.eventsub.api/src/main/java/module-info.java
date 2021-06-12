import perobobbot.lang.JsonModuleProvider;
import perobobbot.twitch.eventsub.api.EventSubModule;

module perobobbot.twitch.event.sub.api {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;

    requires com.google.common;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.datatype.guava;
    requires com.fasterxml.jackson.databind;

    opens perobobbot.twitch.eventsub.api to com.fasterxml.jackson.databind;

    provides JsonModuleProvider with EventSubModule;

    exports perobobbot.twitch.eventsub.api;


}
