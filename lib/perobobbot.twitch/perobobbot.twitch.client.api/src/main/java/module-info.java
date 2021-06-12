module perobobbot.twitch.client.api {
    exports perobobbot.twitch.client.api;
    requires static lombok;
    requires java.desktop;

    requires reactor.core;
    requires perobobbot.oauth;
    requires perobobbot.lang;

    requires transitive perobobbot.twitch.event.sub.api;

    requires com.fasterxml.jackson.annotation;

    opens perobobbot.twitch.client.api to com.fasterxml.jackson.databind;
}
