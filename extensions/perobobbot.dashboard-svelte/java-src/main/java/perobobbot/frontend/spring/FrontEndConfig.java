package perobobbot.frontend.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import perobobbot.lang.Packages;

@Component
public class FrontEndConfig implements WebMvcConfigurer {

    public static final String CONTEXT = "/dashboard";

    public static Packages provider() {
        return Packages.with("[Extension] Frontend", FrontEndConfig.class);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(CONTEXT).setViewName("redirect:"+CONTEXT+"/index.html");
        registry.addViewController(CONTEXT+"/").setViewName("redirect:"+CONTEXT+"/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(CONTEXT+"/**")
                .addResourceLocations("classpath:/dashboard-svelte/public/");
    }


}
