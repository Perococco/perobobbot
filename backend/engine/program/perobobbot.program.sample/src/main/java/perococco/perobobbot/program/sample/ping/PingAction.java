package perococco.perobobbot.program.sample.ping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.IO;
import perobobbot.service.core.Services;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
public class PingAction {

    public static PingAction create(@NonNull Services services) {
        return new PingAction(services.getService(IO.class));
    }

    @NonNull
    private final IO io;

    public void ping(@NonNull ChannelInfo channelInfo, @NonNull Instant receptionTime) {
        io.forChannelInfo(channelInfo)
          .print(d -> {
              final Duration duration = Duration.between(receptionTime, d.getDispatchingTime());
              return "mypong (" + duration.toMillis() + ")";
          });
    }
}
