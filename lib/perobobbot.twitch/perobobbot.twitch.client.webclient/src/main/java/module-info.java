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
    requires spring.context;
    requires reactor.core;
    requires org.aspectj.weaver;

    opens perobobbot.twitch.client.webclient.spring to spring.core, spring.context, spring.bean;

//    provides Packages with TwitchServiceConfiguration;
}
