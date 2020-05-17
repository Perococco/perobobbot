package perobobbot.data.jpa;

import lombok.NonNull;
import perobobbot.common.lang.Packages;
import perobobbot.data.jpa.service.JPAUserService;

public class JPAPackages {

    @NonNull
    public static Packages provider() {
        return Packages.with("DATA_SERVICE", JPAUserService.class);
    }
}
