import perobobbot.common.sound.SoundManager;

module perobobbot.common.sound {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.lang;
    requires com.google.common;

    exports perobobbot.common.sound;

    uses SoundManager.Factory;
}