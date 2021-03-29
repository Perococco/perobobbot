package perobobbot.plugin;

import com.google.common.collect.ImmutableList;
import jplugman.annotation.ExtensionPoint;
import lombok.NonNull;

@ExtensionPoint(version = 1)
public interface WebPlugin extends PerobobbotPlugin {


    @NonNull ImmutableList<ViewInfo> getViewInformation();

    @NonNull ImmutableList<ResourceLocation> getResourceLocations();

    @NonNull ClassLoader resourceClassLoader();


    @Override
    default <T> @NonNull T accept(@NonNull PerobobbotPlugin.Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
