package perococco.perobobbot.program.sample.hello;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.*;
import perobobbot.program.core.ChannelInfo;

import java.util.Optional;

@RequiredArgsConstructor
public class HelloState {

    @NonNull
    @Getter
    private final ImmutableSet<UserOnChannel> alreadyGreeted;

    @NonNull
    @Getter
    private final ImmutableMap<ChannelInfo,ChannelGreetings> greetingsPerChannel;

    @NonNull
    public static HelloState empty() {
        return EMPTY;
    }

    private static final HelloState EMPTY = new HelloState(ImmutableSet.of(),ImmutableMap.of());

    @NonNull
    public Optional<ChannelGreetings> greetingOnChannel(@NonNull ChannelInfo channelInfo) {
        return Optional.ofNullable(greetingsPerChannel.get(channelInfo));
    }

}
