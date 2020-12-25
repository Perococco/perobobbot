package perobobbot.localio;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.fp.Consumer1;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
public class LocalExecutor {

    private final @NonNull PrintStream output;

    private final @NonNull ImmutableMap<String,LocalAction> actions;

    private final @NonNull Consumer1<? super LocalMessage> defaultHandler;

    public LocalExecutor(@NonNull PrintStream output, @NonNull Consumer1<? super LocalMessage> defaultHandler, @NonNull LocalAction...actions) {
        this.output = output;
        this.defaultHandler = defaultHandler;
        this.actions = Arrays.stream(actions).collect(ImmutableMap.toImmutableMap(LocalAction::getName, a -> a));
    }

    public void handleMessage(@NonNull String line) {
        final var cleanedLine = line.trim().toLowerCase();
        if (cleanedLine.equals("help")) {
            this.showHelp();
        }
        final LocalAction localAction = actions.get(cleanedLine);
        if (localAction != null) {
            localAction.getExecution().run();
        } else {
            formLocalMessage(line).ifPresentOrElse(defaultHandler, () -> output.println("[ERROR] Invalid message '"+line+"'"));
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
