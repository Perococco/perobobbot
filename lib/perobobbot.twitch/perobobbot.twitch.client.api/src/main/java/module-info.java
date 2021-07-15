import perobobbot.lang.JsonModuleProvider;
import perobobbot.twitch.client.api.deser.TwitchApiSubModule;

module perobobbot.twitch.client.api {
    requires static lombok;
    requires java.desktop;

    requires reactor.core;
    requires perobobbot.oauth;
    requires perobobbot.lang;

    requires transitive perobobbot.twitch.event.sub.api;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires perobobbot.twitch.api;
    requires perobobbot.http;

    exports perobobbot.twitch.client.api;
    exports perobobbot.twitch.client.api.games;
    exports perobobbot.twitch.client.api.evensub;
    exports perobobbot.twitch.client.api.channel;
    exports perobobbot.twitch.client.api.channelpoints;

    opens perobobbot.twitch.client.api to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.games to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.evensub to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.channel to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.channelpoints to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.deser to com.fasterxml.jackson.databind;


    provides JsonModuleProvider with TwitchApiSubModule;
}
