package perobobbot.frontend.spring;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import perobobbot.lang.Packages;

@Component
public class FrontEndConfig implements WebMvcConfigurer {


    public static Packages provider() {
        return Packages.with("[Extension] Frontend", FrontEndConfig.class);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/dashboard/**")
                .addResourceLocations("classpath:/dashboard/public/");
    }


}
