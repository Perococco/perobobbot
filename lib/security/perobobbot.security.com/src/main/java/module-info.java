module perobobbot.security.com {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;
    requires com.google.common;
    requires spring.security.core;
    requires java.validation;

    opens perobobbot.security.com to com.fasterxml.jackson.databind, org.hibernate.validator;

    exports perobobbot.security.com;
}
