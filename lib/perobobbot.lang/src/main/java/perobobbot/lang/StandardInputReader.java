package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StandardInputReader extends Looper implements StandardInputProvider {

    private final InputStream reference;
    private final BufferedReader reader;

    public boolean shouldExit = false;

    private final Listeners<Consumer1<? super String>> listeners = new Listeners<>();

    public StandardInputReader() {
        this(System.in);
    }

    public StandardInputReader(@NonNull InputStream inputStream) {
        reference = inputStream;
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public @NonNull Subscription addListener(@NonNull Consumer1<? super String> listener) {
        return listeners.addListener(listener);
    }

    @Override
    public void requestStop() {
        shouldExit = true;
        try {
            reference.close();
        } catch (IOException ignored) {}
        super.requestStop();
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final String line = getNextNotBlankLine();
        if (line == null) {
            return IterationCommand.STOP;
        }
        sendMessage(line);
        return IterationCommand.CONTINUE;
    }

    private void sendMessage(@NonNull String line) {
        listeners.warnListeners(c -> c.accept(line));
    }

    public String getNextNotBlankLine() throws IOException {
        while (true) {
            final String line = reader.readLine();
            if (line == null || shouldExit) {
                return null;
            }
            if (!line.isBlank()) {
                return line.trim();
            }
        }
    }

}
