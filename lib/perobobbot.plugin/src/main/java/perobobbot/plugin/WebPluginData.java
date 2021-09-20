package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.Value;

public record WebPluginData(@NonNull ImmutableList<ViewInfo> viewInformation,
                            @NonNull ImmutableList<ResourceLocation> resourceLocations,
                            @NonNull ClassLoader resourceClassLoader) implements PerobobbotPluginData {

    @Override
    public <T> @NonNull T accept(@NonNull PerobobbotPluginData.Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
