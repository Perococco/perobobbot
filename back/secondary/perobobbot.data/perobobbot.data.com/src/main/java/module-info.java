module perobobbot.data.com {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.lang;
    requires transitive perobobbot.security.com;
    requires transitive perobobbot.oauth;

    requires com.google.common;
    requires com.fasterxml.jackson.annotation;

    opens perobobbot.data.com to com.fasterxml.jackson.databind, org.hibernate.validator;

    exports perobobbot.data.com;
    exports perobobbot.data.com.event;
}
