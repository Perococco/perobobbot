package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.PointType;

public interface Promotion {

    Promotion DEFAULT = new Promotion() {
        @Override
        public long initialBalance(@NonNull PointType pointType) {
            return 0;
        }
    };


    long initialBalance(@NonNull PointType pointType);




}
