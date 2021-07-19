package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.PointType;

public enum PromotionManager {
    INSTANCE,
    ;

    private Promotion promotion = Promotion.DEFAULT;

    public static long initialBalance(@NonNull PointType pointType) {
        return INSTANCE.promotion.initialBalance(pointType);
    }

    public @NonNull Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(@NonNull Promotion promotion) {
        this.promotion = promotion;
    }


}
