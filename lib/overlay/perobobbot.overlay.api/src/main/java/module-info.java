
module perobobbot.overlay.api {
    requires static lombok;
    requires java.desktop;

    requires transitive perobobbot.sound;
    requires transitive perobobbot.rendering;
    requires transitive perobobbot.lang;
    requires transitive perobobbot.timeline;

    requires com.google.common;
    requires org.apache.logging.log4j;

    exports perobobbot.overlay.api;

    uses perobobbot.overlay.api.OverlayController.Factory;
}
