package perococco.perobobbot.program.core;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class InstructionExtraction {

    @NonNull
    private final String prefix;

    @NonNull
    private final String instructionName;

    @NonNull
    private final String parameters;

}
