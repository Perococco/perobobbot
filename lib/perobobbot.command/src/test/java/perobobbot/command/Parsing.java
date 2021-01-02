package perobobbot.command;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class Parsing {

    private final @NonNull String fullCommand;
    private final @NonNull String regexp;
    private final int nbParameters;

}
