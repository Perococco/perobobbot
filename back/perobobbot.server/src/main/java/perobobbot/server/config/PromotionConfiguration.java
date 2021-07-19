package perobobbot.server.config;

import org.springframework.context.annotation.Configuration;
import perobobbot.data.com.PromotionManager;
import perobobbot.server.component.DefaultPromotion;

@Configuration
public class PromotionConfiguration {


    public PromotionConfiguration() {
        PromotionManager.INSTANCE.setPromotion(new DefaultPromotion());
    }

}
