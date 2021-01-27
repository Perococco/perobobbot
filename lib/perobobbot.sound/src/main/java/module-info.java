import perobobbot.sound.SoundManager;

module perobobbot.sound {
    requires static lombok;
    requires java.desktop;

    requires org.apache.logging.log4j;
    requires perobobbot.lang;
    requires com.google.common;

    exports perobobbot.sound;

    uses SoundManager.Factory;
}
