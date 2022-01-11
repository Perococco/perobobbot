import perobobbot.data.jpa.DataJpaConfiguration;
import perobobbot.lang.Packages;

module perobobbot.data.jpa {
    requires static lombok;
    requires java.desktop;


    requires org.apache.logging.log4j;
    requires spring.boot.autoconfigure;
    requires spring.data.commons;
    requires spring.security.core;
    requires spring.aop;
    requires spring.data.jpa;
    requires spring.context;
    requires spring.beans;
    requires spring.tx;
    requires spring.core;
    requires org.aspectj.weaver;

    requires perobobbot.security.core;
    requires perobobbot.data.service;
    requires perobobbot.data.domain;
    requires perobobbot.data.com;
    requires perobobbot.oauth;
    requires com.google.common;

    opens perobobbot.data.jpa to spring.beans, spring.context, spring.core, perobobbot.data.jpa.test;
    opens perobobbot.data.jpa.service to spring.core,spring.beans,spring.context, perobobbot.data.jpa.test;
    opens perobobbot.data.jpa.repository to spring.core,spring.beans,spring.context,spring.data.commons, perobobbot.data.jpa.test;
    opens perobobbot.data.jpa.repository.tools to spring.beans, spring.context, spring.core, perobobbot.data.jpa.test;

    provides Packages with DataJpaConfiguration;

    exports perobobbot.data.jpa;
    exports perobobbot.data.jpa.service;
    exports perobobbot.data.jpa.repository;
    exports perobobbot.data.jpa.repository.tools;

}
