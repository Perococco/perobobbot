package perococco.perobobbot.common.lang;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.*;
import perobobbot.common.lang.fp.Function1;

@RequiredArgsConstructor
@Log4j2
public class WithMapIO implements IO {

    @NonNull
    private final ImmutableMap<Platform, PlatformIO> ioByPlatform;

    @Override
    public void print(@NonNull ChannelInfo channelInfo, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final PlatformIO platformIO = ioByPlatform.get(channelInfo.getPlatform());

        if (platformIO != null) {
            platformIO.print(channelInfo.getChannelName(), messageBuilder);
        } else {
            LOG.warn("No IO for platform {}",channelInfo.getPlatform());
        }
    }

 }