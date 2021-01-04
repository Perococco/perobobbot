module perobobbot.rest.com {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;


    opens perobobbot.rest.com to com.fasterxml.jackson.databind, org.hibernate.validator;

    exports perobobbot.rest.com;
}
