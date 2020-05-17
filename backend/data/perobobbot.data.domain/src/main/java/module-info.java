module perobobbot.data.domain {
    requires static lombok;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires java.persistence;
    requires java.validation;
    requires com.google.common;

    requires perobobbot.common.lang;
    requires perobobbot.data.com;

    opens perobobbot.data.domain to org.hibernate.orm.core, org.hibernate.validator, spring.core ;

    exports perobobbot.data.domain;
    exports perobobbot.data.domain.transformers;
}