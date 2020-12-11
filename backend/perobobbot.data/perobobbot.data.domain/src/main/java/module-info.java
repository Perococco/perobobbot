module perobobbot.data.domain {
    requires static lombok;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.data.com;

    requires java.validation;
    requires java.persistence;

    opens perobobbot.data.domain to org.hibernate.orm.core, org.hibernate.validator, spring.core ;

    exports perobobbot.data.domain;
    exports perobobbot.data.domain.transformers;
}