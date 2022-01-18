module perobobbot.discord.resources {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires perobobbot.discord.oauth.api;
    requires transitive perobobbot.oauth;
    requires perobobbot.lang;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    exports perobobbot.discord.resources;

    opens perobobbot.discord.resources to com.fasterxml.jackson.databind;

}