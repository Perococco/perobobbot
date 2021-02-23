package perobobbot.frontend.svelte;

import lombok.NonNull;
import perobobbot.plugin.WebPlugin;

public class FrontEndConfig implements WebPlugin {

    public static final String CONTEXT = "/dashboard";


    @Override
    public @NonNull String getName() {
        return "Svelte FrontEnd";
    }

    @Override
    public void registerView(@NonNull ViewControllerRegistry viewControllerRegistry) {
        viewControllerRegistry.registerView(CONTEXT,"redirect:"+CONTEXT+"/index.html");
        viewControllerRegistry.registerView(CONTEXT+"/","redirect:"+CONTEXT+"/index.html");
    }

    @Override
    public void registerResources(@NonNull ResourceHandlerRegistry resourceHandlerRegistry) {
        resourceHandlerRegistry.addResourceLocation(CONTEXT+"/**", "classpath:/dashboard-svelte/public/");
    }
}
