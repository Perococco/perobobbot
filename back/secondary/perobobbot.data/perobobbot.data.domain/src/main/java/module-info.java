module perobobbot.data.domain {
    requires static lombok;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires com.google.common;

    requires perobobbot.lang;
    requires perobobbot.data.com;
    requires perobobbot.persistence;

    requires java.validation;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.hibernate.validator;
    requires java.sql;

    opens perobobbot.data.domain to org.hibernate.orm.core, org.hibernate.validator, spring.core;
    opens perobobbot.data.domain.base to org.hibernate.orm.core, org.hibernate.validator, spring.core ;
    opens perobobbot.data.domain.converter to org.hibernate.orm.core, org.hibernate.validator, spring.core ;
//    opens perobobbot.data.domain;
//    opens perobobbot.data.domain.base;
//    opens perobobbot.data.domain.converter;

    exports perobobbot.data.domain;
    exports perobobbot.data.domain.base;
}
