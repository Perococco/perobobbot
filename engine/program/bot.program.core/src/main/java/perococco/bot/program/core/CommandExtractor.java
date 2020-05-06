package perococco.bot.program.core;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandExtractor {

    @NonNull
    public static Optional<InstructionExtraction> extract(@NonNull String commandPrefix, @NonNull String message) {
        return new CommandExtractor(commandPrefix, message).extractCommand();
    }

    @NonNull
    private final String commandPrefix;

    @NonNull
    private final String message;

    private String[] tokens;

    private Optional<InstructionExtraction> extractCommand() {
        if (messageDoesNoStartWithPrefix()) {
            return Optional.empty();
        }
        this.splitMessageIntoTokens();
        if (tokens.length == 0) {
            return Optional.empty();
        }

        final String commandName = tokens[0];
        final String parameters = tokens.length == 1?"":tokens[1].trim();

        return Optional.ofNullable(InstructionExtraction.builder()
                                                        .instructionName(commandName)
                                                        .parameters(parameters)
                                                        .build());
    }

    private void splitMessageIntoTokens() {
        this.tokens = message.substring(commandPrefix.length()).split(" ",2);
    }

    private boolean messageDoesNoStartWithPrefix() {
        return !message.startsWith(commandPrefix);
    }

}
