package perobobbot.localio.spring;

import lombok.NonNull;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

import java.util.stream.Stream;

public class LocalIOPlugin implements Plugin {

    @Override
    public @NonNull PluginType type() {
        return PluginType.EXTENSION;
    }

    @Override
    public @NonNull String name() {
        return "Local IO";
    }

    @Override
    public @NonNull Stream<String> packageStream() {
        return Stream.of(LocalIOPlugin.class.getPackageName());
    }
}
