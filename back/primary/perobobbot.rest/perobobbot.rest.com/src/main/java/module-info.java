module perobobbot.rest.com {
    requires static lombok;

    requires java.desktop;
    requires perobobbot.lang;
    requires perobobbot.security.com;
    requires perobobbot.data.com;


    opens perobobbot.rest.com to com.fasterxml.jackson.databind, org.hibernate.validator;

    exports perobobbot.rest.com;
}
