package perobobbot.localio;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.Consumer1;
import perobobbot.lang.fp.Value2;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class SimpleLocalExecutor implements LocalExecutor {

    private final @NonNull PrintStream output;

    private final @NonNull ImmutableMap<String, LocalAction> actions;

    private final @NonNull Consumer1<? super LocalMessage> defaultHandler;

    public SimpleLocalExecutor(@NonNull PrintStream output, @NonNull Consumer1<? super LocalMessage> defaultHandler, @NonNull LocalAction...actions) {
        this.output = output;
        this.defaultHandler = defaultHandler;
        this.actions = Arrays.stream(actions).collect(ImmutableMap.toImmutableMap(LocalAction::getName, a -> a));
    }

    public void handleMessage(@NonNull String line) {
        final var cleanedLine = parse(line.trim());
        if (cleanedLine.getA().equals("help")) {
            this.showHelp();
        }
        final LocalAction localAction = actions.get(cleanedLine.getA());
        if (localAction != null) {
            try {
                localAction.execute(cleanedLine.getB());
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                t.printStackTrace(System.err);
            }
        } else {
            formLocalMessage(line).ifPresentOrElse(defaultHandler, () -> output.println("[ERROR] Invalid message '"+line+"'"));
        }
    }

    public @NonNull Value2<String,String[]> parse(@NonNull String line) {
        final var idx = line.indexOf(" ");
        if (idx < 0) {
            return Value2.of(line.toLowerCase(), new String[0]);
        } else {
            return Value2.of(line.substring(0,idx).toLowerCase(), line.substring(idx+1).split(" "));
        }
    }

    private void showHelp() {
        final var width = actions.keySet().stream().mapToInt(String::length).max().orElse(0);
        if (width<=0) {
            return;
        }
        final var format = "%%-%ds -> %%s%%n".formatted(width);
        actions.keySet()
               .stream()
               .sorted()
               .forEach(k -> output.printf(format,k,actions.get(k).getDescription()));
    }

    private @NonNull Optional<LocalMessage> formLocalMessage(@NonNull String line) {
        final String botName;
        final String message;
        if (line.startsWith("#")) {
            int idx = line.indexOf(' ');
            if (idx<2 || idx+1 >= line.length()) {
                return Optional.empty();
            }
            botName = line.substring(1,idx);
            message = line.substring(idx+1).trim();
        } else {
            botName = "";
            message = line.trim();
        }
        return Optional.of(new LocalMessage(botName, message));
    }

}
