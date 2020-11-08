package perobobbot.overlay.api;

public interface SoundExecution {

    SoundExecution NOP = () -> {};

    /**
     * Cancel the sound (i.e. stop it before its complete execution)
     */
    void cancel();
}
