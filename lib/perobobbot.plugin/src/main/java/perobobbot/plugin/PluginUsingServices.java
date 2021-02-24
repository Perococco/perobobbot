package perobobbot.plugin;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface PluginUsingServices extends Plugin {

    /**
     * @return the set of services required by this plugin
     */
    @NonNull ImmutableSet<Requirement> getRequirements();


}
