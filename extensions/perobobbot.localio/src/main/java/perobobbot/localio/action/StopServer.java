package perobobbot.localio.action;

import lombok.NonNull;
import perobobbot.lang.ApplicationCloser;

public class StopServer extends LocalActionBase {

    private final @NonNull ApplicationCloser applicationCloser;

    public StopServer(@NonNull ApplicationCloser applicationCloser) {
        super("stop","stop the server");
        this.applicationCloser = applicationCloser;
    }

    @Override
    public void execute(@NonNull String[] parameters) {
        applicationCloser.execute();
    }
}
