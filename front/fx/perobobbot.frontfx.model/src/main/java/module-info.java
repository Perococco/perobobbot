module perobobbot.frontfx.model {
    requires static lombok;
    requires java.desktop;
    requires javafx.base;
    requires perobobbot.fx;
    requires perobobbot.lang;
    requires perobobbot.state;
    requires com.google.common;
    requires transitive perobobbot.action;
    requires javafx.graphics;
    requires perobobbot.i18n;

    requires org.apache.logging.log4j;

    exports perobobbot.frontfx.model;
    exports perobobbot.frontfx.model.state;
    exports perobobbot.frontfx.model.dialog;
    exports perobobbot.frontfx.model.state.mutation;
    exports perobobbot.frontfx.model.view;
}
