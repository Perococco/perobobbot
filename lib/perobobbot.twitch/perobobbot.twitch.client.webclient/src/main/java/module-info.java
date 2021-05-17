import perobobbot.lang.Packages;
import perobobbot.twitch.client.webclient.spring.TwitchServiceConfiguration;

module perobobbot.twitch.client.webclient {
    requires static lombok;
    requires java.desktop;
    requires spring.webflux;
    requires perobobbot.lang;
    requires perobobbot.proxy;
    requires transitive perobobbot.twitch.client.api;
    requires spring.web;
    requires perobobbot.oauth;
    requires spring.context;
    requires reactor.core;

    opens perobobbot.twitch.client.webclient.spring to spring.core, spring.context, spring.bean;

//    provides Packages with TwitchServiceConfiguration;
}
