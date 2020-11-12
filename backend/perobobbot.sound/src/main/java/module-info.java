import perobobbot.sound.SoundManager;

module perobobbot.sound {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;
    requires com.google.common;

    exports perobobbot.sound;

    uses SoundManager.Factory;
}