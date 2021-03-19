module perobobbot.fxspring {
    requires static lombok;
    requires java.desktop;
    requires javafx.graphics;
    requires spring.context;
    requires perobobbot.fx;
    requires perobobbot.lang;
    requires perobobbot.spring;
    requires spring.boot;
    requires spring.beans;
    requires perobobbot.i18n;

    requires org.apache.logging.log4j;

    exports perobobbot.fxspring;

    opens perococco.perobobbot.fxspring to spring.core, spring.beans, spring.context;
}
