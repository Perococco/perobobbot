package perococco.perobobbot.program.sample.hello;

import com.google.common.collect.ImmutableSet;
import lombok.*;

@RequiredArgsConstructor
public class HelloState {

    @NonNull
    @Getter
    private final ImmutableSet<String> alreadyGreeted;

    @NonNull
    @Getter
    private final ImmutableSet<GreetingIssuer> greetingIssuers;

    @NonNull
    public static HelloState empty() {
        return EMPTY;
    }

    private static final HelloState EMPTY = new HelloState(ImmutableSet.of(),ImmutableSet.of());


}
