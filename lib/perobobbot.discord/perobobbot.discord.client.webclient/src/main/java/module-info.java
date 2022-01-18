import perobobbot.discord.client.webclient.spring.DiscordApiConfiguration;
import perobobbot.lang.Packages;

module perobobbot.discord.client.webclient {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.lang;
    requires perobobbot.discord.client.api;
    requires reactor.core;
    requires spring.context;
    requires perobobbot.http;
    requires perobobbot.oauth.tools;
    requires spring.webflux;
    requires spring.web;

    requires org.apache.logging.log4j;

    provides Packages with DiscordApiConfiguration;

    opens perobobbot.discord.client.webclient.spring to spring.core, spring.context, spring.beans, spring.aop;
}