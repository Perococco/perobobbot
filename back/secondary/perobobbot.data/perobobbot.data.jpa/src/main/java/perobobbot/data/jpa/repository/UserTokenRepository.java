package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import perobobbot.data.com.UnknownUserToken;
import perobobbot.data.domain.UserTokenEntity;
import perobobbot.lang.Platform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity,Long> {

    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndViewerIdentity_Id(@NonNull String login, @NonNull UUID viewerIdentityId);

    @NonNull Optional<UserTokenEntity> findByOwner_LoginAndPlatformAndViewerIdentity_ViewerId(@NonNull String login, @NonNull Platform platform, @NonNull String viewerId);

    @NonNull Optional<UserTokenEntity> findByUuid(@NonNull UUID uuid);

    @NonNull List<UserTokenEntity> findAllByOwner_Login(@NonNull String ownerLogin);

    default @NonNull UserTokenEntity getByUuid(@NonNull UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new UnknownUserToken(uuid));
    }


}
