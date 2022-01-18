
module perobobbot.discord.client.api {
    requires static lombok;
    requires java.desktop;
    requires transitive perobobbot.discord.resources;

    requires reactor.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires perobobbot.lang;

    exports perobobbot.discord.client.api;
    exports perobobbot.discord.client.api.channel;

    opens perobobbot.discord.client.api.channel to com.fasterxml.jackson.databind;

}