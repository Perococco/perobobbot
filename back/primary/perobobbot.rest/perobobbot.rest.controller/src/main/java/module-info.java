import perobobbot.lang.Packages;
import perobobbot.rest.controller.ControllerConfig;

open module perobobbot.rest.controller {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.data.service;
    requires perobobbot.security.core;
    requires perobobbot.oauth;
    requires perobobbot.lang;
    requires perobobbot.twitch.event.sub.api;
    requires transitive perobobbot.rest.com;

    requires reactor.core;


    requires com.google.common;
    requires org.apache.logging.log4j;
    requires spring.security.core;
    requires spring.web;
    requires spring.context;
    requires java.validation;
    requires perobobbot.backend.i18n;
    requires java.servlet;
    requires org.aspectj.weaver;


    exports perobobbot.rest.controller;

    provides Packages with ControllerConfig;
}
