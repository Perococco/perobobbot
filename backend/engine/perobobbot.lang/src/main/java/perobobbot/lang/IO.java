package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Function1;
import perococco.perobobbot.common.lang.PerococcoIOBuilder;

public interface IO {

    /**
     * Print a message to the provided channel
     * @param channelInfo the channel information to find the platform and the channel to send the message to
     * @param messageBuilder abuilder that can use the {@link DispatchContext} to create the message to send
     */
    void print(@NonNull ChannelInfo channelInfo, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param channelInfo the channel information to find the platform and the channel to send the message to
     * @param message print the message to the provided channel
     */
    default void print(@NonNull ChannelInfo channelInfo , @NonNull String message) {
        print(channelInfo, d -> message);
    }

    @NonNull
    PlatformIO forPlatform(@NonNull Platform platform);

    @NonNull
    default ChannelIO forChannelInfo(@NonNull ChannelInfo channelInfo) {
        return forPlatform(channelInfo.getPlatform()).forChannel(channelInfo.getChannelName());
    }

    static IOBuilder builder() {
        return new PerococcoIOBuilder();
    }
}
