package perobobbot.twitch.chat.spring;

import lombok.NonNull;
import perobobbot.common.lang.Packages;

import java.util.stream.Stream;

public class TwitchIOPackage implements Packages {

    @Override
    public @NonNull String context() {
        return "twitch-io";
    }

    @Override
    public @NonNull Stream<String> stream() {
        return Stream.of(TwitchIOPackage.class.getPackageName());
    }
}
