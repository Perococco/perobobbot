package perobobbot.plugin;

import lombok.NonNull;

public interface WebPlugin extends Plugin {

    void registerView(@NonNull ViewControllerRegistry viewControllerRegistry);

    void registerResources(@NonNull ResourceHandlerRegistry resourceHandlerRegistry);


    interface ViewControllerRegistry {
        void registerView(@NonNull String urlPathOrPattern, @NonNull String viewName);
    }

    interface ResourceHandlerRegistry {
        void addResourceLocation(String[] pathPatterns, String[] resourceLocations);

        default void addResourceLocation(@NonNull String pathPattern, @NonNull String resourceLocation) {
            addResourceLocation(new String[]{pathPattern}, new String[]{resourceLocation});
        }

    }

}
