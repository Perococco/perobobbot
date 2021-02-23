module perobobbot.spring {
    requires static lombok;
    requires java.desktop;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires perobobbot.lang;


    requires com.google.common;

    requires org.apache.logging.log4j;
    requires spring.boot;
    requires perobobbot.plugin;

    exports perobobbot.spring;
}
