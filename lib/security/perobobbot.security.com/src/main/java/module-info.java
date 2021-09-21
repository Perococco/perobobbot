import perobobbot.lang.JsonModuleProvider;
import perobobbot.security.com.deser.SecurityJsonModule;

module perobobbot.security.com {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;
    requires com.google.common;
    requires spring.security.core;
    requires java.validation;
    requires com.fasterxml.jackson.databind;
    requires perobobbot.oauth;

    opens perobobbot.security.com to com.fasterxml.jackson.databind, org.hibernate.validator;

    exports perobobbot.security.com;

    provides JsonModuleProvider with SecurityJsonModule;
}
