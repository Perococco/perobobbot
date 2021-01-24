import perobobbot.fx.KeyTrackerFactory;
import perococco.perobobbot.fx.PeroKeyTrackerFactory;

module perobobbot.fx {
    uses perobobbot.fx.KeyTrackerFactory;
    provides KeyTrackerFactory with PeroKeyTrackerFactory;

    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;
    requires perobobbot.validation;
    requires javafx.graphics;
    requires perobobbot.i18n;

    requires javafx.fxml;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires com.google.common;

    exports perobobbot.fx;
    exports perobobbot.fx.dialog;
}
