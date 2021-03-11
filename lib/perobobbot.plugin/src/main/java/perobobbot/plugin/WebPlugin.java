package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public interface WebPlugin extends PerobobbotPlugin {


    @NonNull ImmutableList<ViewInfo> getViewInformation();

    @NonNull ImmutableList<ResourceLocation> getResourceLocations();

    @Override
    default public <T> @NonNull T accept(@NonNull PerobobbotPlugin.Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
