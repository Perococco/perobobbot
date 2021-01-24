module perobobbot.data.com {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.lang;
    requires transitive perobobbot.security.com;

    requires com.google.common;

    opens perobobbot.data.com to com.fasterxml.jackson.databind, org.hibernate.validator;

    exports perobobbot.data.com;
}
