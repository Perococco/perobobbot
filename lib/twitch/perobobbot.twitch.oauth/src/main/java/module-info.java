module perobobbot.twitch.oauth {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires perobobbot.lang;
    requires spring.web;
    requires perobobbot.http;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    exports perobobbot.twitch.oauth;

    opens perococco.perobobbot.twitch.oauth to com.fasterxml.jackson.databind;
}
