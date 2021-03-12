package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

@Value
public class ResourceLocation {

    public static @NonNull ResourceLocation with(@NonNull String pathPattern, @NonNull String location) {
        return new ResourceLocation(ImmutableList.of(pathPattern),ImmutableList.of(location));
    }

    ImmutableList<String> pathPatterns;

    ImmutableList<String> locations;

}
