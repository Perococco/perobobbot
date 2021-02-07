package perococco.command;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandParsing;

@RequiredArgsConstructor
public class PeroCommandParsing implements CommandParsing {

    @Getter
    private final @NonNull String fullName;

    @Getter
    private final @NonNull String fullParameters;

    @Getter
    private final @NonNull ImmutableMap<String,String> parameterValues;

}
