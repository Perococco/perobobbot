
module perobobbot.overlay.api {
    uses perobobbot.overlay.api.OverlayController.Factory;
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.sound;
    requires transitive perobobbot.rendering;
    requires perobobbot.lang;

    requires devolay;
    requires com.google.common;
    requires org.apache.logging.log4j;

    exports perobobbot.overlay.api;

}