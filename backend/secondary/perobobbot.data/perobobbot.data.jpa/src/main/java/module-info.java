import perobobbot.data.jpa.JpaConfiguration;
import perobobbot.lang.Plugin;

module perobobbot.data.jpa {
    requires static lombok;
    requires java.desktop;
    requires spring.data.jpa;
    requires spring.context;
    requires spring.beans;
    requires spring.tx;
    requires spring.core;

    requires perobobbot.data.service;
    requires perobobbot.data.domain;
    requires perobobbot.data.com;
    requires spring.boot.autoconfigure;
    requires spring.data.commons;
    requires com.google.common;

    opens perobobbot.data.jpa to spring.core;
    opens perobobbot.data.jpa.service to spring.core,spring.beans,spring.context;
    opens perobobbot.data.jpa.repository to spring.core,spring.beans,spring.context,spring.data.commons;

    provides Plugin with JpaConfiguration;

    exports perobobbot.data.jpa;
    exports perobobbot.data.jpa.service;
    exports perobobbot.data.jpa.repository;

}