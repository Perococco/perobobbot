package perobobbot.data.jpa;

import lombok.NonNull;
import perobobbot.data.jpa.service.JPAUserService;
import perobobbot.lang.Packages;

public class JPAPackages {

    @NonNull
    public static Packages provider() {
        return Packages.with("JPA Data", JPAUserService.class);
    }
}
