module perobobbot.frontfx.gui {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires perobobbot.lang;
    requires perobobbot.fxspring;
    requires spring.boot;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires javafx.swing;
    requires javafx.graphics;
    requires javafx.controls;
    requires com.google.common;
    requires perobobbot.fx;
    requires perobobbot.frontfx.model;
    requires perobobbot.i18n;
    requires perobobbot.frontfx.i18n;
    requires perobobbot.frontfx.action;
    requires perobobbot.validation;
    requires perobobbot.rest.client.api;

    opens perobobbot.frontfx.gui to javafx.graphics;

    opens perococco.perobobbot.frontfx.gui to spring.core, spring.beans, spring.context;
    opens perococco.perobobbot.frontfx.gui.dialog to spring.core, spring.beans, spring.context;
    opens perococco.perobobbot.frontfx.gui.view to spring.core, spring.beans, spring.context, javafx.fxml, perobobbot.fx, perobobbot.fxspring;
    opens perococco.perobobbot.frontfx.gui.fxml to spring.core, spring.beans, spring.context, javafx.fxml, perobobbot.fx, perobobbot.fxspring;
    opens perococco.perobobbot.frontfx.gui.style to spring.core, spring.beans, spring.context;
    opens perococco.perobobbot.frontfx.gui.action to spring.core, spring.beans, spring.context;

}
