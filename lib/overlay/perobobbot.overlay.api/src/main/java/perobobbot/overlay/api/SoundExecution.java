package perobobbot.overlay.api;

public interface SoundExecution {

    SoundExecution NOP = new SoundExecution() {
        @Override
        public void cancel() {}

        @Override
        public boolean isDone() { return false;}
    };

    /**
     * Cancel the sound (i.e. stop it before its complete execution)
     */
    void cancel();

    boolean isDone();
}
