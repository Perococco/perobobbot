package perobobbot.data.jpa.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import perobobbot.data.domain.UserTokenEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity,Long> {

    @NonNull Optional<UserTokenEntity> findByUuid(@NonNull UUID uuid);

    @NonNull List<UserTokenEntity> findAllByOwner_Login(@NonNull String ownerLogin);

    int removeByOwner_LoginAndViewerIdentity_UuidAndAccessToken(@NonNull String ownerLogin, @NonNull UUID viewerIdentityId, @NonNull String encryptedAccessToken);

}
