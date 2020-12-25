package perobobbot.greeter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.User;

@RequiredArgsConstructor
public class HelloState {

    @NonNull
    @Getter
    private final ImmutableSet<UserOnChannel> alreadyGreeted;

    @NonNull
    @Getter
    private final ImmutableMap<ChannelInfo,ImmutableSet<User>> greetersPerChannel;

    @NonNull
    public ImmutableSet<User> getGreetersOnChannel(@NonNull ChannelInfo channelInfo) {
        return greetersPerChannel.getOrDefault(channelInfo, ImmutableSet.of());
    }

    public boolean hasBeenGreeted(@NonNull UserOnChannel userOnChannel) {
        return alreadyGreeted.contains(userOnChannel);
    }

    @NonNull
    public static HelloState empty() {
        return EMPTY;
    }

    private static final HelloState EMPTY = new HelloState(ImmutableSet.of(),ImmutableMap.of());


}
