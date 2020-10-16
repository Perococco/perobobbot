package perobobbot.overlay;

public interface SoundExecution {

    SoundExecution NOP = () -> {};

    void cancel();
}
