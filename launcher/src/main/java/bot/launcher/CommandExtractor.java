package bot.launcher;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandExtractor {

    @NonNull
    public static Optional<String> extract(@NonNull String message) {
        return extract("!", message);
    }

    @NonNull
    public static Optional<String> extract(@NonNull String commandPrefix, @NonNull String message) {
        return new CommandExtractor(commandPrefix, message).extractCommand();
    }

    @NonNull
    private final String commandPrefix;

    @NonNull
    private final String message;

    private int indexOfFirstSpace;

    private Optional<String> extractCommand() {
        if (messageDoesNoStartWithPrefix()) {
            return Optional.empty();
        }
        this.findFirstSpaceInMessage();
        if (thereIsNoSpaceInMessage()) {
            return Optional.of(message.substring(commandPrefix.length()));
        }
        return Optional.of(message.substring(commandPrefix.length(), indexOfFirstSpace));
    }

    private boolean messageDoesNoStartWithPrefix() {
        return !message.startsWith(commandPrefix);
    }

    private void findFirstSpaceInMessage() {
        indexOfFirstSpace = message.indexOf(' ');
    }

    private boolean thereIsNoSpaceInMessage() {
        return indexOfFirstSpace < 0;
    }
}
