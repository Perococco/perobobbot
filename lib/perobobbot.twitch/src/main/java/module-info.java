import perobbot.twitch.oauth.spring.TwitchOAuthConfiguration;
import perobobbot.lang.Packages;

module perobobbot.twitch {
    requires static lombok;
    requires java.desktop;
    requires spring.context;
    requires spring.web;
    requires perobobbot.http;
    requires perobobbot.lang;
    requires perobobbot.oauth;
    requires java.servlet;
    requires com.google.common;
    requires org.apache.logging.log4j;


    opens perobbot.twitch.oauth.spring to spring.context, spring.beans, spring.core;

    provides Packages with TwitchOAuthConfiguration;

}
