import perobobbot.data.jpa.JPAPackages;
import perobobbot.lang.Packages;

module perobobbot.data.jpa {
    requires static lombok;
    requires java.desktop;
    requires spring.data.jpa;
    requires spring.context;
    requires spring.beans;
    requires spring.tx;
    requires spring.core;
    requires spring.security.core;

    requires perobobbot.data.service;
    requires perobobbot.data.domain;
    requires perobobbot.data.com;
    requires spring.boot.autoconfigure;
    requires spring.data.commons;

    opens perobobbot.data.jpa.repository to spring.core,spring.beans,spring.context,spring.data.commons;
    opens perobobbot.data.jpa.service to spring.core,spring.beans,spring.context;
    opens perobobbot.data.jpa to spring.core;

    provides Packages with JPAPackages;

    exports perobobbot.data.jpa;
    exports perobobbot.data.jpa.repository;
    exports perobobbot.data.jpa.service;
}