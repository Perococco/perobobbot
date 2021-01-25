module perobobbot.spring {
    uses perobobbot.lang.Plugin;
    requires static lombok;
    requires java.desktop;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires perobobbot.lang;



    requires org.apache.logging.log4j;
    requires spring.boot;

    exports perobobbot.spring;
}
