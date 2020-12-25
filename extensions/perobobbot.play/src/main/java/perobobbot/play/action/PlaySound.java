package perobobbot.play.action;

import lombok.NonNull;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;
import perobobbot.play.PlayExtension;

public class PlaySound implements Executor<ExecutionContext> {

    private final @NonNull PlayExtension extension;

    public PlaySound(PlayExtension extension) {
        this.extension = extension;
    }

    @Override
    public void execute(@NonNull ExecutionContext context) {
        extension.playSound(context.getParameters());
    }
}
