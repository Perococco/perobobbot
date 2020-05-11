module perobobbot.server {
    requires static lombok;

    requires java.desktop;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires com.google.common;

    requires perobobbot.twitch.chat;
    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;
    requires perobobbot.program.core;
    requires perobobbot.program.sample;
    requires perobobbot.blackjack.engine;
    requires spring.web;

    requires org.apache.logging.log4j;

    requires com.fasterxml.classmate;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens bot.server to spring.core;
    opens bot.server.controller to spring.core;

    exports bot.server;
    exports bot.server.controller;
}