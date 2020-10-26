module perobobbot.overlay {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.common.lang;
    requires transitive perobobbot.common.sound;

    requires devolay;
    requires com.google.common;

    requires org.apache.logging.log4j;

    exports perobobbot.overlay;
}