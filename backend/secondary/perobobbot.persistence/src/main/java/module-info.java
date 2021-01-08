module perobobbot.persistence {
    requires static lombok;
    requires java.desktop;

    requires org.hibernate.orm.core;
    requires java.sql;
    requires perobobbot.lang;
    requires java.persistence;
    requires org.apache.logging.log4j;

    opens perobobbot.persistence to perobobbot.data.domain, org.hibernate.orm.core, org.hibernate.validator, spring.core;
    opens perobobbot.persistence.type to perobobbot.data.domain, org.hibernate.orm.core, org.hibernate.validator, spring.core;

    exports perobobbot.persistence;
    exports perobobbot.persistence.type;
}
