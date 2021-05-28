import perobobbot.lang.Packages;
import perobobbot.rest.controller.ControllerConfig;

open module perobobbot.rest.controller {
    requires static lombok;
    requires java.desktop;

    requires perobobbot.data.service;
    requires perobobbot.security.core;
    requires perobobbot.oauth;
    requires perobobbot.lang;
    requires transitive perobobbot.rest.com;



    requires com.google.common;
    requires org.apache.logging.log4j;
    requires spring.security.core;
    requires spring.web;
    requires java.validation;
    requires perobobbot.backend.i18n;
    requires java.servlet;
    requires org.aspectj.weaver;

//    opens perobobbot.rest.controller to spring.core,spring.beans,spring.context,spring.web;

    exports perobobbot.rest.controller;
    exports perobobbot.rest.controller.aspect;

    provides Packages with ControllerConfig;
}
