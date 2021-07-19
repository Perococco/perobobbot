package perobobbot.server.component;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.data.com.Promotion;
import perobobbot.lang.PointType;

@Component
public class DefaultPromotion implements Promotion {

    @Override
    public long initialBalance(@NonNull PointType pointType) {
        return 20_000;
    }
}
