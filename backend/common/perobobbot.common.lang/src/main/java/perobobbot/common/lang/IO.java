package perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.fp.Function1;
import perococco.perobobbot.common.lang.PerococcoIOBuilder;

public interface IO {

    /**
     * Print a message to the IO that received the message that trigger the program
     * @param channelInfo the channel information to find the platform and the channel to send the message to
     * @param messageBuilder the builder that can use the {@link DispatchContext} to create the message
     */
    void print(@NonNull ChannelInfo channelInfo, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder);

    /**
     * @param channelInfo the channel information to find the platform and the channel to send the message to
     * @param message print the message to the IO that received the message that trigger the program
     */
    default void print(@NonNull ChannelInfo channelInfo , @NonNull String message) {
        print(channelInfo, d -> message);
    }

    @NonNull
    default PlatformIO forPlatform(@NonNull Platform platform) {
        return (channel, messageBuilder) -> IO.this.print(new ChannelInfo(platform, channel), messageBuilder);
    }

    @NonNull
    default ChannelIO forChannelInfo(@NonNull ChannelInfo channelInfo) {
        return messageBuilder -> IO.this.print(channelInfo,messageBuilder);
    }

    static IOBuilder builder() {
        return new PerococcoIOBuilder();
    }
}
