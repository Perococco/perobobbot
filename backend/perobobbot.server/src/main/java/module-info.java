module perobobbot.server {
    uses perobobbot.common.lang.Packages;
    requires static lombok;

    requires java.desktop;
    requires spring.context;
    requires spring.web;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires com.google.common;

    requires perobobbot.twitch.chat;
    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;
    requires perobobbot.data.domain;
    requires perobobbot.data.com;
    requires perobobbot.data.jpa;
    requires perobobbot.data.service;
    requires perobobbot.program.core;
    requires perobobbot.program.sample;
    requires perobobbot.blackjack.engine;


    requires org.apache.logging.log4j;

    requires com.fasterxml.classmate;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires spring.security.web;
    requires spring.data.jpa;
    requires spring.tx;
    requires jjwt;
    requires org.flywaydb.core;

    opens db.migration;
    opens perobobbot.server to spring.core,spring.beans,spring.context;
    opens perobobbot.server.controller to spring.core,spring.beans,spring.context;
    opens perobobbot.server.config.security to spring.core,spring.beans,spring.context;
    opens perobobbot.server.config.security.jwt to spring.core,spring.beans,spring.context;

    requires net.bytebuddy;
    requires spring.beans;
    requires spring.orm;
    requires spring.data.commons;

    exports perobobbot.server;
    exports perobobbot.server.controller;
}