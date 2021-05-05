
module perobobbot.server {
    requires static lombok;

    requires java.desktop;
    requires spring.context;
    requires spring.web;
    requires spring.core;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.messaging;
    requires spring.websocket;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires jakarta.websocket.api;

    requires org.eclipse.jetty.websocket.api;

    requires com.google.common;

    requires transitive perobobbot.lang;
    requires perobobbot.access;
    requires perobobbot.plugin;
    requires perobobbot.chat.advanced;
    requires perobobbot.chat.core;
    requires perobobbot.command;
    requires perobobbot.data.com;
    requires perobobbot.data.service;
    requires perobobbot.extension;
    requires perobobbot.spring;
    requires perobobbot.messaging;
    requires perobobbot.overlay.api;
    requires perobobbot.security.com;
    requires perobobbot.sound;
    requires perobobbot.http;
    requires perobobbot.oauth;

    requires org.apache.logging.log4j;

    requires com.fasterxml.classmate;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires spring.security.messaging;
    requires spring.security.web;
    requires spring.webflux;
    requires spring.data.jpa;
    requires spring.tx;
    requires jjwt;
    requires org.flywaydb.core;
    requires java.xml.bind;



    requires jplugman.api;
    requires jplugman.manager;
    requires jplugman.tools;

    opens db.migration;
    opens perobobbot.server to spring.core,spring.beans,spring.context, spring.web;

    opens perobobbot.server.oauth to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.websocket to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.extension to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.command to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.io to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.tray to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.externaluri to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.security to spring.core,spring.beans,spring.context, spring.web;
    opens perobobbot.server.config.security.jwt to spring.core,spring.beans,spring.context, spring.web;
    opens perobobbot.server.plugin to spring.core,spring.beans,spring.context, spring.web, velocity.engine.core;
    opens perobobbot.server.plugin.template to spring.core,spring.beans,spring.context, spring.web, velocity.engine.core;
    opens perobobbot.server.plugin.extension to spring.core,spring.beans,spring.context, spring.web;
    opens perobobbot.server.plugin.webplugin to spring.core,spring.beans,spring.context, spring.web;

    opens perobobbot.server.component to spring.core,spring.beans,spring.context, spring.web, spring.messaging;

    opens template;
    opens template.parent;
    opens template.src.main.assembly;
    opens template.src.main.java;
    opens template.src.main.java.groupId;

    requires net.bytebuddy;
    requires spring.beans;
    requires spring.orm;
    requires spring.data.commons;
    requires java.servlet;
    requires java.validation;
    requires spring.webmvc;
    requires spring.integration.core;
    requires reactor.core;

    requires velocity.engine.core;

    requires spring.boot.actuator;
    requires org.eclipse.jetty.websocket.server;
    requires java.sql;
    requires java.naming;
    requires com.h2database;
    requires perobobbot.security.core;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.datatype.guava;

    exports perobobbot.server;
}
