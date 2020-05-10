module bot.launcher {
    requires static lombok;

    requires java.desktop;
    requires spring.context;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires com.google.common;

    requires bot.twitch.chat;
    requires bot.common.lang;
    requires bot.chat.advanced;
    requires bot.program.core;
    requires bot.program.sample;
    requires bot.blackjack.engine;
    requires spring.web;

    requires com.fasterxml.classmate;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    opens bot.launcher to spring.core;
    opens bot.launcher.controller to spring.core;

    exports bot.launcher;
    exports bot.launcher.controller;
}