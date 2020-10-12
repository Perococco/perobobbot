module bot.overlay {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.common.lang;
    requires devolay;
    requires com.google.common;

    requires org.apache.logging.log4j;

    exports perobobbot.overlay;
    exports perobobbot.overlay.sample;
}