module perobobbot.twitch.client.api {
    requires static lombok;
    requires java.desktop;

    requires reactor.core;
    requires perobobbot.oauth;
    requires perobobbot.lang;

    requires transitive perobobbot.twitch.event.sub.api;

    requires com.fasterxml.jackson.annotation;

    exports perobobbot.twitch.client.api;
    exports perobobbot.twitch.client.api.games;
    exports perobobbot.twitch.client.api.evensub;
    exports perobobbot.twitch.client.api.channelpoints;

    opens perobobbot.twitch.client.api to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.games to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.evensub to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;
    opens perobobbot.twitch.client.api.channelpoints to com.fasterxml.jackson.databind, perobobbot.twitch.client.aop;

}
