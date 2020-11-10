import perobobbot.overlay.api.OverlayController;

module perobobbot.overlay.api {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.common.sound;
    requires perobobbot.common.lang;

    requires devolay;
    requires com.google.common;
    requires org.apache.logging.log4j;

    exports perobobbot.overlay.api;

    uses OverlayController.Factory;
}