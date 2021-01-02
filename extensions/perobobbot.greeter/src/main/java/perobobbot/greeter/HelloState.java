package perobobbot.greeter;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.User;
import perobobbot.lang.fp.Value2;
import perobobbot.lang.fp.Value3;

@RequiredArgsConstructor
public class HelloState {

    @NonNull
    @Getter
    private final ImmutableSet<Value3<ChatConnectionInfo,User,ChannelInfo>> alreadyGreeted;

    @NonNull
    @Getter
    private final ImmutableMap<ChannelInfo,ImmutableSet<Value2<ChatConnectionInfo,User>>> greetersPerChannel;

    @NonNull
    public ImmutableSet<Value2<ChatConnectionInfo,User>> getGreetersOnChannel(@NonNull ChannelInfo channelInfo) {
        return greetersPerChannel.getOrDefault(channelInfo, ImmutableSet.of());
    }

    public boolean hasBeenGreeted(@NonNull Value3<ChatConnectionInfo,User,ChannelInfo> triplet) {
        return alreadyGreeted.contains(triplet);
    }

    @NonNull
    public static HelloState empty() {
        return EMPTY;
    }

    private static final HelloState EMPTY = new HelloState(ImmutableSet.of(),ImmutableMap.of());

}
