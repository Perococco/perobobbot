module perobobbot.fxspring {
    uses perobobbot.plugin.Plugin;
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
    requires perobobbot.plugin;

    exports perobobbot.fxspring;

    opens perococco.perobobbot.fxspring to spring.core, spring.beans, spring.context;
}
