import perobobbot.overlay.api.OverlayController;
import perobobbot.overlay.newtek.NewtekFactory;

module perobobbot.overlay.newtek {
    requires static lombok;
    requires java.desktop;


    requires perobobbot.overlay.api;
    requires perobobbot.common.lang;

    requires org.apache.logging.log4j;
    requires com.google.common;
    requires devolay;

    provides OverlayController.Factory with NewtekFactory;
}