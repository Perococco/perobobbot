import perobobbot.frontfx.action.list.PluginProvider;
import perobobbot.lang.Plugin;

module perobobbot.frontfx.action {
    requires static lombok;
    requires java.desktop;

    requires com.google.common;
    requires perobobbot.action;
    requires perobobbot.lang;
    requires spring.context;
    requires perobobbot.frontfx.model;
    requires javafx.base;
    requires javafx.controls;

    requires org.apache.logging.log4j;
    requires perobobbot.validation;
    requires perobobbot.fx;
    requires perobobbot.rest.client.api;

    exports perobobbot.frontfx.action;
    exports perobobbot.frontfx.action.list;

    provides Plugin with PluginProvider;
}
