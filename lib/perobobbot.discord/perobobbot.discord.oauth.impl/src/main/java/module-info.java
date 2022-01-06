import perobobbot.discord.oauth.impl.spring.DiscordOAuthConfiguration;
import perobobbot.lang.Packages;

module perobobbot.discord.oauth.impl {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires spring.context;
    requires spring.webflux;

    requires perobobbot.lang;
    requires perobobbot.oauth;
    requires perobobbot.discord.resources;
    requires perobobbot.discord.oauth.api;
    requires spring.web;
    requires spring.core;
    requires com.fasterxml.jackson.annotation;
    requires java.servlet;


    opens perobobbot.discord.oauth.impl.spring to spring.context, spring.beans, spring.core;

    provides Packages with DiscordOAuthConfiguration;

}