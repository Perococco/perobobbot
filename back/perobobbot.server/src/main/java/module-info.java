import perobobbot.lang.JsonModuleProvider;

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


    requires perobobbot.twitch.client.api;

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
    requires perobobbot.security.com;
    requires perobobbot.http;
    requires perobobbot.eventsub;
    requires perobobbot.oauth;
    requires perobobbot.oauth.tools;

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

    requires perobobbot.template;


    requires jplugman.api;
    requires jplugman.manager;
    requires jplugman.tools;

    opens db.migration;
    opens perobobbot.server to spring.core,spring.beans,spring.context, spring.web;

    opens perobobbot.server.sse to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.sse.impl to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.oauth to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.eventsub to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.websocket to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.extension to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.command to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.io to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.tray to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.validation to spring.core,spring.beans,spring.context, spring.web, spring.messaging, com.fasterxml.jackson.databind;
    opens perobobbot.server.config.externaluri to spring.core,spring.beans,spring.context, spring.web, spring.messaging;
    opens perobobbot.server.config.security to spring.core,spring.beans,spring.context, spring.web;
    opens perobobbot.server.config.security.jwt to spring.core,spring.beans,spring.context, spring.web;
    opens perobobbot.server.plugin to spring.core,spring.beans,spring.context, spring.web, velocity.engine.core;
    opens perobobbot.server.plugin.template to spring.core,spring.beans,spring.context, spring.web, velocity.engine.core;
    opens perobobbot.server.plugin.extension to spring.core,spring.beans,spring.context, spring.web;
    opens perobobbot.server.plugin.webplugin to spring.core,spring.beans,spring.context, spring.web;

    opens perobobbot.server.component to spring.core,spring.beans,spring.context, spring.web, spring.messaging;

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

    requires io.github.classgraph;
    requires spring.data.rest.core;
    requires org.springdoc.openapi.ui;
    requires org.springdoc.openapi.common;
    requires org.springdoc.openapi.webmvc.core;
    requires org.springdoc.openapi.hateoas;
    requires org.springdoc.openapi.data.rest;
    requires org.springdoc.openapi.security;
    requires swagger.ui;
    requires webjars.locator.core;
    requires io.swagger.v3.oas.models;
    requires io.swagger.v3.oas.integration;
    requires io.swagger.v3.core;
    requires io.swagger.v3.oas.annotations;
    requires org.reactivestreams;
    requires javax.el;
    requires java.annotation;


    uses JsonModuleProvider;

    exports perobobbot.server;
}
