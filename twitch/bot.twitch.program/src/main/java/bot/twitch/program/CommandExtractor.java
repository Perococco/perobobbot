package bot.twitch.program;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandExtractor {

    @NonNull
    public static Optional<ProgramCommand> extract(@NonNull String message) {
        return extract("!", message);
    }

    @NonNull
    public static Optional<ProgramCommand> extract(@NonNull String commandPrefix, @NonNull String message) {
        return new CommandExtractor(commandPrefix, message).extractCommand();
    }

    @NonNull
    private final String commandPrefix;

    @NonNull
    private final String message;

    private String[] tokens;

    private Optional<ProgramCommand> extractCommand() {
        if (messageDoesNoStartWithPrefix()) {
            return Optional.empty();
        }
        this.splitMessageIntoTokens();
        if (tokens.length == 0) {
            return Optional.empty();
        }

        final String commandName = tokens[0];
        final ImmutableList<String> parameters = Arrays.stream(tokens,1,tokens.length).collect(ImmutableList.toImmutableList());

        return Optional.ofNullable(ProgramCommand.builder().name(commandName).parameters(parameters).build());
    }

    private void splitMessageIntoTokens() {
        this.tokens = message.substring(commandPrefix.length()).split(" ");
    }

    private boolean messageDoesNoStartWithPrefix() {
        return !message.startsWith(commandPrefix);
    }

}
