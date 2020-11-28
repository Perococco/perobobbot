package perococco.perobobbot.twitch.chat;

import com.google.common.collect.ImmutableList;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import lombok.NonNull;

import java.time.Duration;

public class Bandwidths {

    private static ImmutableList<Bandwidth> create(int capacity, @NonNull Duration duration) {
        return ImmutableList.of(
                createDirect(capacity,duration),
                createSplitToMilli(capacity,duration)
        );
    }

    private static Bandwidth createDirect(int capacity,@NonNull Duration duration) {
        return Bandwidth.classic(capacity,Refill.intervally(capacity,duration));
    }

    private static Bandwidth createSplitToMilli(int capacity,@NonNull Duration duration) {
        final double perMillisecond = capacity/(double)duration.toMillis();
        return createDirect((int)Math.ceil(perMillisecond), Duration.ofMillis(1));
    }



    private static final Duration _30_SECONDS = Duration.ofSeconds(30);

    public static final ImmutableList<Bandwidth> _20_per_30_seconds = create(20, _30_SECONDS);
    public static final ImmutableList<Bandwidth> _100_per_30_seconds = create(100, _30_SECONDS);
    public static final ImmutableList<Bandwidth> _50_per_30_seconds = create(50, _30_SECONDS);
    public static final ImmutableList<Bandwidth> _7500_per_30_seconds = create(7500, _30_SECONDS);



}
