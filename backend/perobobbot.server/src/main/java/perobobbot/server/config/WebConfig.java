package perobobbot.server.config;

import lombok.Builder;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.websocket.server.ServerContainer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
//        configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
    }


    @Bean
    public ServerContainer serverWebSocket(@NonNull ServletContext servletContext) {
        ServerContainer container = (ServerContainer)servletContext.getAttribute(ServerContainer.class.getName());
        System.out.println("ServerContainer: "+container);
        return container;
    }


}
