package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import perobobbot.data.com.UnknownPlatformUser;
import perobobbot.data.com.UnknownPlatformUserId;
import perobobbot.data.domain.PlatformUserEntity;
import perobobbot.lang.Platform;
import perobobbot.lang.UserIdentity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface PlatformUserRepositoryBase<I extends UserIdentity,T extends PlatformUserEntity<I>> extends JpaRepository<T,Long> {

    @NonNull List<T> findAllFromUserInfo(@NonNull String viewerInfo);

    @NonNull Optional<T> findByUserId(@NonNull String userId);

    @NonNull Optional<T> findByUuid(@NonNull UUID uuid);

    default @NonNull T getByUserId(@NonNull String userId) {
        return findByUserId(userId).orElseThrow(() -> new UnknownPlatformUser(getPlatform(), userId));
    }

    default @NonNull T getByUuid(@NonNull UUID viewIdentityId) {
        return findByUuid(viewIdentityId).orElseThrow(() -> new UnknownPlatformUserId(viewIdentityId));
    }

    @NonNull Platform getPlatform();


}
