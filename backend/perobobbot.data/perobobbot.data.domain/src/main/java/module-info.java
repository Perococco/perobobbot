module perobobbot.data.domain {
    requires static lombok;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.data.com;

    requires java.validation;
    requires java.persistence;
    requires org.hibernate.orm.core;

    opens perobobbot.data.domain to org.hibernate.orm.core, org.hibernate.validator, spring.core ;

    exports perobobbot.data.domain;
}
