import perobobbot.frontfx.gui.spring.GUIConfiguration;
import perobobbot.lang.Plugin;

module perobobbot.ext.frontfx.gui {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires perobobbot.lang;
    requires spring.boot;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires javafx.graphics;
    requires javafx.controls;

    provides Plugin with GUIConfiguration;

    opens perobobbot.frontfx.gui to javafx.graphics;
    opens perobobbot.frontfx.gui.spring to spring.context, spring.beans, spring.core;

}
