import perobobbot.data.jpa.DataJpaConfiguration;
import perobobbot.lang.Packages;

module perobobbot.data.jpa {
    requires static lombok;
    requires java.desktop;
    requires spring.data.jpa;
    requires spring.context;
    requires spring.beans;
    requires spring.tx;
    requires spring.core;
    requires org.aspectj.weaver;

    requires org.apache.logging.log4j;

    requires perobobbot.security.core;
    requires perobobbot.data.service;
    requires perobobbot.data.domain;
    requires perobobbot.data.com;
    requires perobobbot.oauth;
    requires spring.boot.autoconfigure;
    requires spring.data.commons;
    requires com.google.common;
    requires spring.security.core;

    opens perobobbot.data.jpa to spring.core;
    opens perobobbot.data.jpa.service to spring.core,spring.beans,spring.context;
    opens perobobbot.data.jpa.repository to spring.core,spring.beans,spring.context,spring.data.commons;

    provides Packages with DataJpaConfiguration;

    exports perobobbot.data.jpa;
    exports perobobbot.data.jpa.service;
    exports perobobbot.data.jpa.repository;

}
